package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.Constants;
import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.business.service.RoomService;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentRef;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.jpa.repository.EquipmentRefRepository;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private RoomService roomService;
    @Autowired
    private EquipmentRepository equipmentRepo;
    @Autowired
    private EquipmentRefService equipmentRefService;

    @Autowired
    private EquipmentRefRepository equipmentRefRepo;

    public Equipment createEquipment(Equipment equipment) {

        Room stockage = roomService.getRoom(Constants.ROOM_STOCKAGE);

        EquipmentRef equipmentRef = equipmentRefService.getOrCreateReference(equipment.getReferenceName());

        Equipment newEquipment = createSingleEquipment(stockage, equipment);

        return equipmentRepo.save(newEquipment);
    }

    public Equipment getEquipment(String serialNumber, String referenceName) {
        return equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, referenceName)
                .orElseThrow(() -> new EquipmentNotFoundException(referenceName));
    }


    private Equipment createSingleEquipment(Room stockage, Equipment equipment) {

        //On vérifie si l'equipement est déjà en base
        String serialNumber = equipment.getSerialNumber();
        equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, equipment.getReferenceName())
                .ifPresent(e -> {
                    throw new DuplicateEquipmentException( equipment.getReferenceName(),equipment.getSerialNumber() );
                });

        equipment.setRoom(stockage);

       return equipment;
    }

    @Override
    public void deleteEquipment(String serialNumber, String referenceName) {
        // Récupérer l'équipement
        Equipment equipment = equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, referenceName)
                .orElseThrow(() -> new EquipmentNotFoundException(referenceName + " (SN: " + serialNumber + ")"));

        // Décrémenter la quantité de la référence
        equipmentRefService.decrementQuantity(referenceName);

        // Supprimer l'équipement
        equipmentRepo.delete(equipment);
    }

}


