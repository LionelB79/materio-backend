package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
Optional<Equipment> findByReferenceName(String name);
}
