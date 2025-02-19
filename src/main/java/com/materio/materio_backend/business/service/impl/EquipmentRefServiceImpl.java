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
    private EquipmentRefRepository equipmentRefRepo;

    @Override
    public EquipmentRef getOrCreateReference(final String referenceName) {
        return equipmentRefRepo.findByName(referenceName)
                .map(this::incrementQuantity)
                .orElseGet(() -> createNewReference(referenceName));
    }

    @Override
    public EquipmentRef getReference(final String referenceName) {
        return equipmentRefRepo.findByName(referenceName)
                .orElseThrow(() -> new ReferenceNotFoundException(referenceName));
    }

    private EquipmentRef incrementQuantity(final EquipmentRef ref) {
        ref.setQuantity(ref.getQuantity() + 1);
        return equipmentRefRepo.save(ref);
    }

    private EquipmentRef createNewReference(final String referenceName) {
        final EquipmentRef newRef = new EquipmentRef();
        newRef.setName(referenceName);
        newRef.setQuantity(1);
        return equipmentRefRepo.save(newRef);
    }

    @Override
    public void decrementQuantity(final String referenceName) {
        final EquipmentRef ref = getReference(referenceName);
        if (ref.getQuantity() <= 0) {
            throw new InvalidQuantityException(referenceName);
        }
        ref.setQuantity(ref.getQuantity() - 1);
        equipmentRefRepo.save(ref);
    }
}
