package com.materio.materio_backend.service;

import com.materio.materio_backend.model.BO.EquipmentBO;
import com.materio.materio_backend.model.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    Equipment createEquipment(EquipmentBO equipmentBO);
    List<Equipment> getAllEquipments();
    List<Equipment> getEquipmentsByRoom(String roomName);
    List<Equipment> getEquipmentsByReference(String referenceName);
    void deleteEquipment(Long id);
}
