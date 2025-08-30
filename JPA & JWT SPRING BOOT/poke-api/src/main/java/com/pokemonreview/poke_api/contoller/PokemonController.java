package com.pokemonreview.poke_api.contoller;

import com.pokemonreview.poke_api.dto.PokemonDto;
import com.pokemonreview.poke_api.dto.PokemonResponse;
import com.pokemonreview.poke_api.models.Pokemon;
import com.pokemonreview.poke_api.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {
    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping()
    public ResponseEntity<PokemonResponse> getPokemons(
              @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
              @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){

            return new ResponseEntity<>(pokemonService.getAllPokemons(pageNo, pageSize), HttpStatus.OK);
//        List<Pokemon> pokemons = new ArrayList<>();
//        pokemons.add(new Pokemon(1L, "Pikachu", "Electric"));
//        pokemons.add(new Pokemon(2L, "Charmander", "Fire"));
//        pokemons.add(new Pokemon(3L, "Bulbasaur", "Grass"));
//        pokemons.add(new Pokemon(4L, "Squirtle", "Water"));
//        return ResponseEntity.ok(pokemons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonDto> getPokemon(@PathVariable Long id){
//        return new Pokemon(id, "Pikachu", "Electric");
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {

        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable("id") Long pokemonId) {
        System.out.println(pokemonDto.getName());
        System.out.println(pokemonDto.getType());
        PokemonDto response = pokemonService.updatePokemon(pokemonId, pokemonDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") Long pokemonId) {
//        System.out.println("Pokemon with ID " + pokemonId + " deleted.");
//        return ResponseEntity.noContent().build();
        pokemonService.deletePokemon(pokemonId);
        return new ResponseEntity<>("Pokemon with ID " + pokemonId + " deleted.", HttpStatus.OK);
    }
}
