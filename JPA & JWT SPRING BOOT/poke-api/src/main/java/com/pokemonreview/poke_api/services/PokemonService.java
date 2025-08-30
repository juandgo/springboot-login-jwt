package com.pokemonreview.poke_api.services;

import com.pokemonreview.poke_api.dto.PokemonDto;
import com.pokemonreview.poke_api.dto.PokemonResponse;

import java.util.List;

public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemons(int pageNo, int pageSize);
    PokemonDto getPokemonById(Long id);
    PokemonDto updatePokemon(Long id, PokemonDto pokemonDto);
    void deletePokemon(Long id);
}
