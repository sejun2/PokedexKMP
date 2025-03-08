package com.sejun2.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sejun2.shared.domain.model.PokemonSummary
import com.sejun2.shared.domain.usecase.GetPokemonSummaryListUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class PokemonSummaryListViewModel constructor(private val getPokemonSummaryListUseCase: GetPokemonSummaryListUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private val _searchValue = MutableStateFlow("")
    val searchValue = _searchValue.asStateFlow()

    @OptIn(FlowPreview::class)
    val isSearchMode = _searchValue.debounce(250.milliseconds).map {
        it.isEmpty()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )

    private var currentOffset = 0
    private val limit = 30
    private var isLoading = false
    private val _pokemonList = mutableListOf<PokemonSummary>()

    init {
        fetchPokemonList()
        setOnSearchValueChange()
    }

    private fun fetchPokemonList() {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true

            getPokemonSummaryListUseCase.execute(currentOffset, limit)
                .onStart {
                    if (currentOffset == 0) {
                        _uiState.value = PokemonListUiState.Loading
                    }
                }
                .catch { e ->
                    println(e.message)
                    _uiState.value =
                        PokemonListUiState.Error(e.message ?: "Unknown error occurred")
                    isLoading = false
                }
                .collect { pokemonList ->
                    _pokemonList.addAll(pokemonList.pokemonSummaryList)
                    _uiState.value = PokemonListUiState.Success(_pokemonList.toList())
                    currentOffset += limit
                    filterPokemonList()
                    isLoading = false
                }
        }
    }

    fun loadMore() {
        fetchPokemonList()
    }

    fun search(value: String) {
        _searchValue.value = value
    }

    @OptIn(FlowPreview::class)
    private fun setOnSearchValueChange() {
        _searchValue.debounce(340).distinctUntilChanged().onEach {
            filterPokemonList()
        }.launchIn(viewModelScope)
    }

    private fun filterPokemonList() {
        if (_uiState.value is PokemonListUiState.Success) {
            val filteredList = _pokemonList.filter {
                it.name.contains(searchValue.value, ignoreCase = true)
            }

            _uiState.value = PokemonListUiState.Success(
                pokemonList = filteredList
            )
        }
    }
}

sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(val pokemonList: List<PokemonSummary>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}
