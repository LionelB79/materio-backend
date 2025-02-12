package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.BO.EquipmentBO;
import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.jpa.entity.EquipmentRef;
import com.materio.materio_backend.jpa.repository.EquipmentRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentRefServiceImpl implements EquipmentRefService {

    @Autowired
    EquipmentRefRepository equipmentRefRepo;
    @Override
    public EquipmentRef createNewEquipmentRef(EquipmentBO equipmentBO) {
        EquipmentRef newRef = new EquipmentRef();
        newRef.setName(equipmentBO.getReferenceName());
        newRef.setQuantity(equipmentBO.getQuantity());
        return equipmentRefRepo.save(newRef);
    }
}
