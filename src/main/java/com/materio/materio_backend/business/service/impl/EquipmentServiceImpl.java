package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.Constants;
import com.materio.materio_backend.business.exception.ReferenceNotFoundException;
import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.business.service.RoomService;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
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

    @Override
    public Equipment createEquipment(EquipmentBO equipmentBO) {

        Room stockage = roomService.getRoom(Constants.ROOM_STOCKAGE);

        EquipmentRef equipmentRef = equipmentRefService.getOrCreateReference(equipmentBO.getReferenceName());

        Equipment newEquipment = createSingleEquipment(stockage, equipmentBO);

        return equipmentRepo.save(newEquipment);
    }

    private Equipment createSingleEquipment(Room stockage, EquipmentBO equipmentBO) {

        //On vérifie si l'equipement est déjà en base
        String serialNumber = equipmentBO.getSerialNumber();
        equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, equipmentBO.getReferenceName())
                .ifPresent(e -> {
                    throw new DuplicateEquipmentException(equipmentBO.getReferenceName(), equipmentBO.getSerialNumber());
                });

        Equipment equipment = new Equipment();
        equipment.setSerialNumber(equipmentBO.getSerialNumber());
        equipment.setReferenceName(equipmentBO.getReferenceName());
        equipment.setPurchaseDate(equipmentBO.getPurchaseDate());
        equipment.setDescription(equipmentBO.getDescription());
        equipment.setMark(equipmentBO.getMark());
        equipment.setRoom(stockage);


        return equipment;
    }
    @Override
    public Equipment getEquipment(String serialNumber, String referenceName) {
        return equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, referenceName)
                .orElseThrow(() -> new EquipmentNotFoundException(referenceName));
    }

    @Override
    public void deleteEquipment(String serialNumber, String referenceName) {
        // On récupère l'équipement
        Equipment equipment = getEquipment(serialNumber, referenceName);

        // On décrémente la ref de l'equipement supprimé
        equipmentRefService.decrementQuantity(referenceName);

        equipmentRepo.delete(equipment);
    }

    @Override
    public Equipment updateEquipment(EquipmentBO equipmentBO) {

        Equipment equipment = getEquipment(equipmentBO.getSerialNumber(), equipmentBO.getReferenceName());
        equipment.setPurchaseDate(equipmentBO.getPurchaseDate());
        equipment.setDescription(equipmentBO.getDescription());
        equipment.setMark(equipmentBO.getMark());
        equipment.setRoom(roomService.getRoom(equipmentBO.getRoomName()));


        return equipmentRepo.save(equipment);
    }
}


