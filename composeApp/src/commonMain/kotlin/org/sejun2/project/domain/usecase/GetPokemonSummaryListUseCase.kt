package com.sejun2.shared.domain.usecase

import com.sejun2.shared.domain.model.PokemonSummaryList
import com.sejun2.shared.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonSummaryListUseCase constructor(private val pokemonRepository: IPokemonRepository) {
    suspend fun execute(offset: Int, limit: Int): Flow<PokemonSummaryList> {
        return pokemonRepository.getPokemonList(offset, limit)
            .map { originalList ->
                PokemonSummaryList(
                    pokemonSummaryList = originalList.pokemonSummaryList,
                    count = originalList.count,
                    next = originalList.next,
                    previous = originalList.previous
                )
            }
    }
}