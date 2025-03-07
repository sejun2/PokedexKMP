package com.sejun2.shared.domain.repository

import com.sejun2.shared.domain.model.PokemonDetail
import com.sejun2.shared.domain.model.PokemonSummaryList
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList>
    suspend fun getPokemonDetail(pokemonIndex: Int): Flow<PokemonDetail>
    suspend fun getPokemonDetail(pokemonName: String): Flow<PokemonDetail>
}