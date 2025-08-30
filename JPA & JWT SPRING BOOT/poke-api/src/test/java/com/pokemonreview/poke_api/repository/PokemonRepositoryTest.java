package com.pokemonreview.poke_api.repository;

import com.pokemonreview.poke_api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class PokemonRepositoryTest {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRespository_SaveAll_ReturnSavedPokemon() {

        //Arrange
        Pokemon pokemon = new Pokemon().builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }
    @Test
    public void PokemonRepository_FindById_ReturnPokemon() {
        // Arrange
        Pokemon pokemon1 = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemonList = pokemonRepository.findById(pokemon1.getId()).orElse(null);

        // Assert
        Assertions.assertThat(pokemonList).isNotNull();
    }
    @Test
    public void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        // Arrange
        Pokemon pokemon1 = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemonList = pokemonRepository.findByType(pokemon1.getType()).get();

        // Assert
        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
        // Arrange
        Pokemon pokemon1 = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        pokemonRepository.save(pokemon1);

        Pokemon pokemonSaved = pokemonRepository.findById(pokemon1.getId()).orElse(null);
        pokemonSaved.setType("Electric");
        pokemonSaved.setName("Pikachu Updated");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonSaved);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        // Arrange
        Pokemon pokemon1 = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();

        pokemonRepository.save(pokemon1);

        pokemonRepository.deleteById(pokemon1.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon1.getId());
        // Assert
        Assertions.assertThat(pokemonReturn).isEmpty();
    }
}
