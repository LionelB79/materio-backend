package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.jpa.entity.Equipment;

public interface EquipmentService {
    Equipment createEquipment(EquipmentBO equipment);
    void deleteEquipment(String serialNumber, String referenceName);
    Equipment getEquipment(String serialNumber, String referenceName);
    Equipment updateEquipment(EquipmentBO equipmentBO);

}
