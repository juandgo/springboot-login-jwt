package com.pokemonreview.poke_api.services.impl;

import com.pokemonreview.poke_api.dto.ReviewDto;
import com.pokemonreview.poke_api.exceptions.PokemonNotFoundException;
import com.pokemonreview.poke_api.exceptions.ReviewNotFoundException;
import com.pokemonreview.poke_api.models.Pokemon;
import com.pokemonreview.poke_api.models.Review;
import com.pokemonreview.poke_api.repository.PokemonRepository;
import com.pokemonreview.poke_api.repository.ReviewRepository;
import com.pokemonreview.poke_api.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

    @Autowired
    public ResviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(Long pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        review.setPokemon(pokemon);
        Review savedReview = reviewRepository.save(review);
        return mapToDto(savedReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(Long pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);
        return reviews.stream()
                .map(review -> mapToDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(Long reviewId,Long pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Pokemon with associated review not found"));

        if (!review.getPokemon().getId().equals(pokemon.getId())) {
            throw new ReviewNotFoundException("Review with id " + reviewId + " not found for Pokemon with id " + pokemonId);
        }

//        if (review.getPokemon().getId() != pokemon.getId()) {
//            throw new ReviewNotFoundException("Review with id " + reviewId + " not found for Pokemon with id " + pokemonId);
//        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(Long reviewId, Long pokemonId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Pokemon with associated review not found"));

        if (!review.getPokemon().getId().equals(pokemon.getId())) {
            throw new ReviewNotFoundException("Review with id " + reviewId + " not found for Pokemon with id " + pokemonId);
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
//        Review updatedReview = reviewRepository.save(review);

        return mapToDto(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long reviewId, Long pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Pokemon with associated review not found"));

        if (!review.getPokemon().getId().equals(pokemon.getId())) {
            throw new ReviewNotFoundException("Review with id " + reviewId + " not found for Pokemon with id " + pokemonId);
        }

        reviewRepository.delete(review);
    }


    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }

}
