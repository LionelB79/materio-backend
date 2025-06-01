package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Recherche un utilisateur par son nom d'utilisateur
     */
    Optional<User> findByUsername(String username);

    /**
     * Recherche un utilisateur par son email
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un nom d'utilisateur existe déjà
     */
    Boolean existsByUsername(String username);

    /**
     * Vérifie si un email existe déjà
     */
    Boolean existsByEmail(String email);
}
