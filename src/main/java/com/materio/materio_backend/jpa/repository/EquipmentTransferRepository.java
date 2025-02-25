package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentTransferRepository extends JpaRepository<EquipmentTransfer, Long> {
    List<EquipmentTransfer> findByEquipmentIdReferenceNameAndEquipmentIdSerialNumber(
            String referenceName, String serialNumber);
}
