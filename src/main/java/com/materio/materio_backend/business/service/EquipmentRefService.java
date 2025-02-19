package com.materio.materio_backend.business.service;

import com.materio.materio_backend.jpa.entity.EquipmentRef;

public interface EquipmentRefService {
    EquipmentRef getOrCreateReference(String referenceName);
    void decrementQuantity(String referenceName);
    EquipmentRef getReference(String referenceName);
}
