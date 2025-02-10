package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.EquipmentRef;
import com.materio.materio_backend.jpa.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentRefRepository extends JpaRepository<EquipmentRef, String> {

    Optional <EquipmentRef> findByName(String name);
}
