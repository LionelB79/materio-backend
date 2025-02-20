package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, EquipmentPK> {
    Optional<Equipment> findByIdSerialNumberAndIdReferenceName(String serialNumber, String referenceName);

    Set<Equipment> findByZoneNameAndZoneSpaceNameAndZoneSpaceLocalityName(
            String zoneName,
            String spaceName,
            String localityName);
}
