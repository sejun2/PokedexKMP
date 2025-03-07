package com.sejun2.shared.domain.usecase

import com.sejun2.shared.domain.repository.IPokemonRepository

class GetPokemonDetailUseCase constructor(private val pokemonRepository: IPokemonRepository) {
    suspend fun execute(pokemonName: String) = pokemonRepository.getPokemonDetail(pokemonName)
    suspend fun execute(pokemonIndex: Int) = pokemonRepository.getPokemonDetail(pokemonIndex)
}