package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
    Optional<Locality> findByName(String name);
}
