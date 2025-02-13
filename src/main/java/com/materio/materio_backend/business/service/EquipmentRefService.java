package com.materio.materio_backend.business.service;

import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentRef;

public interface EquipmentRefService {
    EquipmentRef createNewEquipmentRef(Equipment equipment);
}
