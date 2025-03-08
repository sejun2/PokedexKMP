package org.sejun2.project.core

import kotlinx.serialization.Serializable

sealed class Routes{

    @Serializable
    data object Home: Routes()

    @Serializable
    data class About(
        val pokemonId: Int
    ): Routes()
}