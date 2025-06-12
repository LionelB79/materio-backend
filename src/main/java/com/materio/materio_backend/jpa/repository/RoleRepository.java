package com.materio.materio_backend.jpa.repository;


import com.materio.materio_backend.jpa.entity.user.EnumRole;
import com.materio.materio_backend.jpa.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Recherche un rôle par son nom
     */
    Optional<Role> findByName(EnumRole name);
}
