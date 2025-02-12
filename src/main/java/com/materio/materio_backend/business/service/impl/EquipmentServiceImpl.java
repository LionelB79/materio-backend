package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.Constants;
import com.materio.materio_backend.business.BO.EquipmentBO;
import com.materio.materio_backend.business.BO.RoomBO;
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

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private EquipmentRefRepository equipmentRefRepo;
    @Autowired
    private EquipmentRepository equipmentRepo;

    public void createEquipment(EquipmentBO equipmentBO) {
        System.out.println("Recherche de la salle : " + Constants.ROOM_STOCKAGE);

        Room stockage = roomRepo.findByName(Constants.ROOM_STOCKAGE)
                .orElseThrow(() -> new RuntimeException("La salle de stockage n'existe pas !"));

        EquipmentRef equipmentRef = equipmentRefRepo.findByName(equipmentBO.getReferenceName())
                .orElseGet(() -> {
                    EquipmentRef newRef = new EquipmentRef();
                    newRef.setName(equipmentBO.getReferenceName());
                    newRef.setQuantity(0);
                    return equipmentRefRepo.save(newRef);
                });

        equipmentRef.setQuantity(equipmentRef.getQuantity() + equipmentBO.getQuantity());
        equipmentRefRepo.save(equipmentRef);

        for (int i = 0; i < equipmentBO.getQuantity(); i++) {
            Equipment equipment = new Equipment();
            equipment.setDescription(equipmentBO.getDescription());
            equipment.setMark(equipmentBO.getMark());
            equipment.setReferenceName(equipmentBO.getReferenceName());
            equipment.setRoom(stockage);



            equipmentRepo.save(equipment);
        }
        roomRepo.save(stockage);
    }

}


