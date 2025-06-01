package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findBySerialNumberAndReferenceName(String serialNumber, String referenceName);

    boolean existsBySerialNumberAndReferenceName(String serialNumber, String referenceName);

    // Nouvelles méthodes utilisant les IDs
    Set<Equipment> findByZoneId(Long zoneId);

    Set<Equipment> findByZoneSpaceId(Long spaceId);

    Set<Equipment> findByZoneSpaceLocalityId(Long localityId);
    Set<Equipment> findByZoneNameAndZoneSpaceNameAndZoneSpaceLocalityName(
            String zoneName, String spaceName, String localityName);

    Set<Equipment> findByZoneSpaceNameAndZoneSpaceLocalityName(
            String spaceName, String localityName);

    Set<Equipment> findByZoneSpaceLocalityName(String localityName);
}