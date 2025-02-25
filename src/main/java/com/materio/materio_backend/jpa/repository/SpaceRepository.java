package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SpaceRepository extends JpaRepository<Space,Long> {
    Optional<Space> findByName(String name);
    Optional<Space> findByNameAndLocality_Name(String name, String localityName);

    Set<Space> findByLocality_Name(String localityName);
}
