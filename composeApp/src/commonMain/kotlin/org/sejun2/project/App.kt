package org.sejun2.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sejun2.shared.presentation.widget.HeadTitleView
import com.sejun2.shared.presentation.widget.PokemonType
import com.sejun2.shared.presentation.widget.PokemonTypeView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            HeadTitleView("Pokemon Types", MaterialTheme.colors.primary)
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