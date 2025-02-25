package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.jpa.entity.Equipment;

import java.util.List;
import java.util.Set;

public interface EquipmentService {
    EquipmentBO createEquipment(EquipmentBO equipmentBO);
    void deleteEquipment(String serialNumber, String referenceName);
    EquipmentBO getEquipment(String serialNumber, String referenceName);
    EquipmentBO updateEquipment(EquipmentBO equipmentBO);
    Set<EquipmentBO> getEquipmentsByZone(String localityName, String spaceName, String zoneName);
    Set<EquipmentBO> getEquipmentsBySpace(String localityName, String spaceName);
    Set<EquipmentBO> getEquipmentsByLocality(String localityName);
    Set<EquipmentBO> getAllEquipments();
}
