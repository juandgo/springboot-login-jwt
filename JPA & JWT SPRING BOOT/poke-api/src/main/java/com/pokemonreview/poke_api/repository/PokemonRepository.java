package com.pokemonreview.poke_api.repository;

import com.pokemonreview.poke_api.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findByType(String type);
}
