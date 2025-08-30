package com.pokemonreview.poke_api.repository;

import com.pokemonreview.poke_api.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPokemonId(Long pokemonId);
}
