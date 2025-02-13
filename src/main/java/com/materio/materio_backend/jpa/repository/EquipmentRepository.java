package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, EquipmentPK> {
    // Utilisation de la notation point pour accéder aux propriétés de la clé composée
    @Query("SELECT e FROM Equipment e WHERE e.id.serialNumber = :serialNumber AND e.id.referenceName = :referenceName")
    Optional<Equipment> findBySerialNumberAndReferenceName(
            @Param("serialNumber") String serialNumber,
            @Param("referenceName") String referenceName
    );
}
