package com.sejun2.shared.data.repository

import com.sejun2.shared.data.dto.toDomain
import com.sejun2.shared.domain.model.PokemonDetail
import com.sejun2.shared.domain.model.PokemonSummaryList
import com.sejun2.shared.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sejun2.project.data.network.api.PokemonApiService

class PokemonRepository constructor(private val pokemonApiService: PokemonApiService) :
    IPokemonRepository {

    private val pokemonDetailCache by lazy {
        mutableMapOf<Int, PokemonDetail>()
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList> = flow {
        val res = pokemonApiService.getPokemonSummaryList(offset, limit)
        emit(res.toDomain())
    }

    override suspend fun getPokemonDetail(pokemonIndex: Int): Flow<PokemonDetail> = flow {
        val cachedDetail = getPokemonDetailFromCacheOrNull(pokemonIndex = pokemonIndex)

        if (cachedDetail != null) {
            emit(cachedDetail)
        } else {
            val res = pokemonApiService.getPokemonDetail(pokemonIndex)
            val pokemonDetail = res.toDomain()
            emit(setPokemonDetailForCache(pokemonDetail))
        }
    }


    override suspend fun getPokemonDetail(pokemonName: String): Flow<PokemonDetail> = flow {
        val res = pokemonApiService.getPokemonDetail(pokemonName)
        emit(res.toDomain())
    }

    private fun getPokemonDetailFromCacheOrNull(pokemonIndex: Int): PokemonDetail? =
        pokemonDetailCache[pokemonIndex]


    private fun setPokemonDetailForCache(pokemonDetail: PokemonDetail): PokemonDetail {
        pokemonDetailCache[pokemonDetail.index] = pokemonDetail

        return pokemonDetail
    }
}