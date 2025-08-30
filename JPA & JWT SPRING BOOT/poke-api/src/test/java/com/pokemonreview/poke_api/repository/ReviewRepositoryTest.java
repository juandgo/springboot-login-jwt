package com.pokemonreview.poke_api.repository;

import com.pokemonreview.poke_api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview() {
        // 1. Arrange: Create and save the Pokemon dependency first
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        Pokemon savedPokemon = pokemonRepository.save(pokemon); // Save the Pokemon

        // 2. Act: Create the Review and link it to the saved Pokemon
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .pokemon(savedPokemon) // <-- Crucial step: link the saved Pokemon
                .build();

        Review savedReview = reviewRepository.save(review);

        // 3. Assert: Verify the Review was saved correctly
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }
}
