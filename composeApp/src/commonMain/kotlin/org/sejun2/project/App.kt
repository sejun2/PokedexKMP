package org.sejun2.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sejun2.shared.presentation.screen.PokemonDetailScreen
import com.sejun2.shared.presentation.screen.PokemonListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sejun2.project.core.Routes

@Composable
@Preview
fun App(modifier: Modifier = Modifier) {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.Home
        ) {
            composable<Routes.Home> {
                PokemonListScreen {
                    navController.navigate(
                        Routes.About(
                            pokemonId = it
                        )
                    )
                }
            }
            composable<Routes.About> {
                val pokemonId = it.toRoute<Routes.About>().pokemonId

                PokemonDetailScreen(
                    pokemonId = pokemonId,
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}