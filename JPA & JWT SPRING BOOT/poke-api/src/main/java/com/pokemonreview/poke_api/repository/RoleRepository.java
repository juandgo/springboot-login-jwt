package com.pokemonreview.poke_api.repository;

import com.pokemonreview.poke_api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // This interface extends JpaRepository, which provides basic CRUD operations

    Optional<Role> findByName(String name);

    // Additional query methods can be defined here if needed
    // For example, to find a role by name:
    // Optional<Role> findByName(String name);
}
