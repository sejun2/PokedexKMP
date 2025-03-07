package org.sejun2.project.data.network.api

import com.sejun2.shared.data.dto.PokemonDetailDto
import com.sejun2.shared.data.dto.PokemonListDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import org.sejun2.project.data.network.ApiClient

class PokemonApiService {
    suspend fun getPokemonSummaryList(offset: Int, limit: Int): PokemonListDto {
        return ApiClient.httpClient.get {
            url {
                takeFrom("https://pokeapi.co/api/v2/pokemon")
                parameters.append("offset", offset.toString())
                parameters.append("limit", limit.toString())
            }
        }.body()
    }

    suspend fun getPokemonDetail(pokemonIndex: Int): PokemonDetailDto {
        return ApiClient.httpClient.get {
            url {
                takeFrom("https://pokeapi.co/api/v2/pokemon/$pokemonIndex")
            }
        }.body()
    }

    suspend fun getPokemonDetail(pokemonName: String): PokemonDetailDto {
        return ApiClient.httpClient.get {
            url {
                takeFrom("https://pokeapi.co/api/v2/pokemon/$pokemonName")
            }
        }.body()
    }

}