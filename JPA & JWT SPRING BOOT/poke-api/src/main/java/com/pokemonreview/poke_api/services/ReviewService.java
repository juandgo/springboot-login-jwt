package com.pokemonreview.poke_api.services;

import com.pokemonreview.poke_api.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(Long pokemonId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByPokemonId(Long pokemonId);
    ReviewDto getReviewById(Long reviewId, Long pokemonId);
    ReviewDto updateReview(Long reviewId, Long pokemonId, ReviewDto reviewDto);
    void deleteReview(Long reviewId, Long pokemonId);
}
