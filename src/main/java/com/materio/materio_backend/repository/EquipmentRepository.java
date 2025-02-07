package com.materio.materio_backend.repository;

import com.materio.materio_backend.model.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}
