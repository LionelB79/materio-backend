package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentTransferRepository extends JpaRepository<EquipmentTransfer, Long> {
    List<EquipmentTransfer> findByEquipmentId(Long equipmentId);

    // Utiliser une requête JPQL pour chercher par les propriétés de l'équipement
    @Query("SELECT et FROM EquipmentTransfer et " +
            "WHERE et.equipment.referenceName = :referenceName " +
            "AND et.equipment.serialNumber = :serialNumber")
    List<EquipmentTransfer> findByEquipmentReferenceNameAndEquipmentSerialNumber(
            @Param("referenceName") String referenceName,
            @Param("serialNumber") String serialNumber);
}