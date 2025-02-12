package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.EquipmentRef;
import com.materio.materio_backend.jpa.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EquipmentRefRepository extends JpaRepository<EquipmentRef, String> {

    Optional <EquipmentRef> findByName(String name);
}
