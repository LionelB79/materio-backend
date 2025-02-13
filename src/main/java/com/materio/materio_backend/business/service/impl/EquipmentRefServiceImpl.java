package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentRef;
import com.materio.materio_backend.jpa.repository.EquipmentRefRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
public class EquipmentRefServiceImpl implements EquipmentRefService {

    @Autowired
    EquipmentRefRepository equipmentRefRepo;
    @Override
    public EquipmentRef createNewEquipmentRef(Equipment equipment) {
        EquipmentRef newRef = new EquipmentRef();
        newRef.setName(equipment.getReferenceName());
        newRef.setQuantity(0);
        return equipmentRefRepo.save(newRef);
    }
}
