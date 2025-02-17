package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.InvalidQuantityException;
import com.materio.materio_backend.business.exception.ReferenceNotFoundException;
import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.jpa.entity.EquipmentRef;
import com.materio.materio_backend.jpa.repository.EquipmentRefRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EquipmentRefServiceImpl implements EquipmentRefService {

    @Autowired
    EquipmentRefRepository equipmentRefRepo;

    public EquipmentRef getOrCreateReference(String referenceName) {
        return equipmentRefRepo.findByName(referenceName)
                .map(this::incrementQuantity)
                .orElseGet(() -> createNewReference(referenceName));
    }

    @Override
    public EquipmentRef getReference(String referenceName) {
        return equipmentRefRepo.findByName(referenceName)
                .orElseThrow(() -> new ReferenceNotFoundException(referenceName));
    }

    private EquipmentRef incrementQuantity(EquipmentRef ref) {
        ref.setQuantity(ref.getQuantity() + 1);
        return equipmentRefRepo.save(ref);
    }

    private EquipmentRef createNewReference(String referenceName) {
        var newRef = new EquipmentRef();
        newRef.setName(referenceName);
        newRef.setQuantity(1);
        return equipmentRefRepo.save(newRef);
    }

    @Override
    public void decrementQuantity(String referenceName) {
        EquipmentRef ref = getReference(referenceName);
        if (ref.getQuantity() <= 0) {
            throw new InvalidQuantityException("La quantité ne peut pas être négative pour la référence : " + referenceName);
        }
        ref.setQuantity(ref.getQuantity() - 1);
        equipmentRefRepo.save(ref);
    }
}
