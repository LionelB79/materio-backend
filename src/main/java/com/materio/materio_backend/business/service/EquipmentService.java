package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.jpa.entity.Equipment;

import java.util.List;
import java.util.Set;

public interface EquipmentService {
    EquipmentBO createEquipment(EquipmentBO equipmentBO);
    void deleteEquipment(Long id);
    void deleteEquipmentBySerialAndReference(String serialNumber, String referenceName);
    EquipmentBO getEquipment(Long id);
    EquipmentBO getEquipmentBySerialAndReference(String serialNumber, String referenceName);
    EquipmentBO updateEquipment(Long id, EquipmentBO equipmentBO);
    Set<EquipmentBO> getEquipmentsByZone(String localityName, String spaceName, String zoneName);


    Set<EquipmentBO> getEquipmentsByLocality(String localityName);
    Set<EquipmentBO> getAllEquipments();
    Set<EquipmentBO> getEquipmentsByZoneId(Long zoneId);
    Set<EquipmentBO> getEquipmentsBySpaceId(Long spaceId);
    Set<EquipmentBO> getEquipmentsByLocalityId(Long localityId);
}