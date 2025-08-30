package com.pokemonreview.poke_api.contoller;

import com.pokemonreview.poke_api.dto.ReviewDto;
import com.pokemonreview.poke_api.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class ReviewContoller {
    private final ReviewService reviewService;

    @Autowired
    public ReviewContoller(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{pokemonId}/create")
    public ResponseEntity<ReviewDto> createReview(@PathVariable Long pokemonId, @RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.createReview(pokemonId, reviewDto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{pokemonId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable Long pokemonId) {
        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long pokemonId, @PathVariable Long reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long pokemonId, @PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(reviewId, pokemonId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long pokemonId, @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId, pokemonId);
        return new ResponseEntity<>("Review deleted successfully",HttpStatus.OK);
    }
}
