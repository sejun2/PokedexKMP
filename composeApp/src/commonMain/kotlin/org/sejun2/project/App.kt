package org.sejun2.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sejun2.shared.data.repository.PokemonRepository
import com.sejun2.shared.presentation.widget.HeadTitleView
import com.sejun2.shared.presentation.widget.PokemonType
import com.sejun2.shared.presentation.widget.PokemonTypeView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sejun2.project.data.network.api.PokemonApiService

@Composable
@Preview
fun App(modifier: Modifier = Modifier) {
    MaterialTheme {
        val coroutineScope = rememberCoroutineScope()

        Column(modifier.fillMaxWidth().safeContentPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
            HeadTitleView("Pokemon Types", MaterialTheme.colors.primary)
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        PokemonRepository(
                            pokemonApiService = PokemonApiService()
                        ).getPokemonDetail(3).collect {
                            println("call getPokemonDetail: $it")
                        }
                    }
                }
            ){
            Text("Get Pokemon Detail")
            }
            PokemonTypeView(
                types = listOf(
                    PokemonType.BUG,
                    PokemonType.DARK,
                    PokemonType.DRAGON,
                    PokemonType.ELECTRIC,
                    PokemonType.FAIRY,
                    PokemonType.FIGHTING,
                    PokemonType.FIRE,
                    PokemonType.FLYING,
                    PokemonType.GHOST,
                    PokemonType.GRASS,
                    PokemonType.GROUND,
                    PokemonType.ICE,
                    PokemonType.NORMAL,
                    PokemonType.POISON,
                    PokemonType.PSYCHIC,
                    PokemonType.ROCK,
                    PokemonType.STEEL,
                    PokemonType.WATER
                )
            )
        }
    }
}