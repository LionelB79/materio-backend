package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.Constants;
import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.business.service.EquipmentService;
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
    private RoomRepository roomRepo;
    @Autowired
    private EquipmentRefRepository equipmentRefRepo;
    @Autowired
    private EquipmentRepository equipmentRepo;
    @Autowired
    private EquipmentRefService equipmentRefService;

    public Equipment createEquipment(Equipment equipment) {

        Room stockage = roomRepo.findByName(Constants.ROOM_STOCKAGE)
                .orElseThrow(() -> new RoomNotFoundException(Constants.ROOM_STOCKAGE));

        EquipmentRef equipmentRef = equipmentRefRepo.findByName(equipment.getReferenceName())
                .orElseGet(() -> equipmentRefService.createNewEquipmentRef(equipment));

        equipmentRef.setQuantity(equipmentRef.getQuantity()+1);

        Equipment newEquipment = createSingleEquipment(stockage, equipment);

        return equipmentRepo.save(newEquipment);
    }


    private Equipment createSingleEquipment(Room stockage, Equipment equipment) {

        //On vérifie si l'equipement est déjà en base
        String serialNumber = equipment.getSerialNumber();
        equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, equipment.getReferenceName())
                .ifPresent(e -> {
                    throw new DuplicateEquipmentException( equipment.getReferenceName(),equipment.getSerialNumber() );
                });

       return equipment;
    }

}


