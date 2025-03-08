package com.sejun2.shared.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.pokemons.ui.theme.Primary
import com.example.pokemons.util.capitalizeFirstLowercaseRest
import com.example.pokemons.util.toPokedexIndex
import com.sejun2.shared.data.repository.PokemonRepository
import com.sejun2.shared.domain.model.PokemonSummary
import com.sejun2.shared.domain.usecase.GetPokemonSummaryListUseCase
import com.sejun2.shared.presentation.viewmodel.PokemonListUiState
import com.sejun2.shared.presentation.viewmodel.PokemonSummaryListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sejun2.project.data.network.api.PokemonApiService
import pokedexkmp.composeapp.generated.resources.Res
import pokedexkmp.composeapp.generated.resources.ic_pokedex_logo
import pokedexkmp.composeapp.generated.resources.ic_search

@Composable
fun PokemonListScreen(
    navigateToPokemonDetail: (Int) -> Unit
) {
    val viewmodel = remember {
        PokemonSummaryListViewModel(
            GetPokemonSummaryListUseCase(
                pokemonRepository = PokemonRepository(
                    pokemonApiService = PokemonApiService()
                )
            )
        )
    }
    Scaffold { innerInsets ->
        Box(Modifier.padding(innerInsets)) {
            PokemonListView(
                navigateToPokemonDetail = navigateToPokemonDetail,
                pokemonSummaryListViewModel = viewmodel
            )
        }
    }
}

@Stable
@Composable
fun PokemonCard(
    pokemon: PokemonSummary,
    onClick: () -> Unit
) {
    val localContext = LocalPlatformContext.current

    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(0.8f)
    ) {
        // Background card with shadow and border
        Card(
            modifier = Modifier
                .fillMaxSize()
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    onClick()
                },
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                // Pokemon number
                Text(
                    text = "#${pokemon.index.toString().toPokedexIndex()}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(bottom = 4.dp, top = 4.dp, end = 4.dp)
                )

                // Pokemon image
                AsyncImage(
                    model =
                        ImageRequest.Builder(context = localContext).data(
                            pokemon.imageSrc
                        ).crossfade(true).size(120).build(),
                    contentDescription = "Pokemon image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(120.dp)
                        .zIndex(1f)
                )

                // Pokemon name
                Surface(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            text = pokemon.name.capitalizeFirstLowercaseRest(),
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun PokemonListHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Primary)
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(28.dp)
                .padding(end = 6.dp),
            painter = painterResource(
                Res.drawable.ic_pokedex_logo
            ),
            contentDescription = "logo_icon",
        )
        Text(
            text = "Pokédex",
            style = MaterialTheme.typography.h3.copy(color = Color.White),
        )
    }
}

@Composable
fun PokedexSearchBar(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp, vertical = 12.dp
            ),
        placeholder = { Text("Input pokemon name") },
        leadingIcon = {
            Image(
                painterResource(
                    Res.drawable.ic_search,
                ),
                contentDescription = "ic_search",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.White),
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(
            100
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            leadingIconColor = Color.White,
        )
    )
}

@Composable
fun PokemonListView(
    pokemonSummaryListViewModel: PokemonSummaryListViewModel,
    navigateToPokemonDetail: (Int) -> Unit
) {
    val uiState by pokemonSummaryListViewModel.uiState.collectAsState()
    val lazyGridState = rememberSaveable(saver = LazyGridState.Saver) {
        LazyGridState()
    }

    var searchValue = pokemonSummaryListViewModel.searchValue.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary)
    ) {
        PokemonListHeader()
        PokedexSearchBar(searchValue) {
            searchValue = it
            pokemonSummaryListViewModel.search(searchValue)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        12.dp
                    ),
                )
                .clip(shape = RoundedCornerShape(12.dp))
        ) {
            when (val state = uiState) {
                is PokemonListUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("tag_circular_progress_indicator")
                )

                is PokemonListUiState.Error -> Text(
                    "Error: $state",
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.Center),
                )

                is PokemonListUiState.Success -> {
                    PokemonList(
                        pokemonList = state.pokemonList,
                        lazyGridState = lazyGridState,
                        onLoadMore = { pokemonSummaryListViewModel.loadMore() },
                        onItemClick = navigateToPokemonDetail,
                        isSearchMode = pokemonSummaryListViewModel.isSearchMode.collectAsState().value
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<PokemonSummary>,
    lazyGridState: LazyGridState,
    onLoadMore: () -> Unit,
    onItemClick: (Int) -> Unit,
    isSearchMode: Boolean
) {
    LazyVerticalGrid(
        columns = Fixed(3),
        state = lazyGridState
    ) {
        items(pokemonList, key = { it.index }) { pokemon ->
            PokemonCard(pokemon) {
                onItemClick(pokemon.index)
            }
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index == lazyGridState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (isSearchMode) return@LaunchedEffect

        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                onLoadMore()
            }
    }
}

@Preview
@Composable
fun PreviewPokemonCard() {
    PokemonCard(pokemon = PokemonSummary("TestName", "TestUrl", 1)) {}
}

@Preview
@Composable
fun PreviewPokemonListScreen() {
    PokemonListScreen {}
}