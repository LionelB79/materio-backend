package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Optional<Zone> findByNameAndSpaceId(String zoneName, Long spaceId);
    List<Zone> findBySpaceId(Long spaceId);
    boolean existsByNameAndSpaceId(String name, Long spaceId);

    // Méthodes de compatibilité si nécessaire
    Optional<Zone> findByNameAndSpaceNameAndSpaceLocalityName(
            String zoneName, String spaceName, String localityName);
}
