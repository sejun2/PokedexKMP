package com.sejun2.shared.domain.model

data class PokemonSummaryList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val pokemonSummaryList: List<PokemonSummary>
)
