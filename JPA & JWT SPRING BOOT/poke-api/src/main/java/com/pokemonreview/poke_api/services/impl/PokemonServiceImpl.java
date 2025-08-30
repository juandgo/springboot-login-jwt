package com.pokemonreview.poke_api.services.impl;

import com.pokemonreview.poke_api.dto.PokemonDto;
import com.pokemonreview.poke_api.dto.PokemonResponse;
import com.pokemonreview.poke_api.exceptions.PokemonNotFoundException;
import com.pokemonreview.poke_api.models.Pokemon;
import com.pokemonreview.poke_api.repository.PokemonRepository;
import com.pokemonreview.poke_api.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    private PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }


    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        PokemonDto savedPokemonDto = new PokemonDto();
        savedPokemonDto.setId(savedPokemon.getId());
        savedPokemonDto.setName(savedPokemon.getName());
        savedPokemonDto.setType(savedPokemon.getType());
        return savedPokemonDto;
    }

    @Override
    public PokemonResponse getAllPokemons(int pageNo, int pageSize) {
//        Pokemon pokemon3 = pokemonRepository.findById(3333L).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found by ID: 3333"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemons = pokemons.getContent();
        List<PokemonDto> contente = listOfPokemons.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(contente);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());
        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonById(Long id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found by ID: " + id));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(Long id, PokemonDto pokemonDto) {
        Pokemon existingPokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found by ID: " + id));

        existingPokemon.setName(pokemonDto.getName());
        existingPokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(existingPokemon);
        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemon(Long id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found by ID: " + id));
        pokemonRepository.delete(pokemon);
    }

    private  PokemonDto mapToDto(Pokemon pokemon) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(pokemonDto.getId());
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}
