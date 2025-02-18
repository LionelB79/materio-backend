package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.EquipmentLocationMismatchException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.business.service.RoomService;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.EquipmentTransferRepository;
import com.materio.materio_backend.jpa.repository.RoomRepository;
import com.materio.materio_backend.dto.Transfer.EquipmentTransfertDTO;
import com.materio.materio_backend.dto.Transfer.TransferRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class EquipmentTransferServiceImpl implements EquipmentTransferService {

    @Autowired
    private RoomService roomService;
    @Autowired
    private EquipmentRepository equipmentRepo;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentTransferRepository equipmentTransferRepo;

    @Override
    public List<EquipmentTransfer> processTransfer(final String locality, final TransferRequestDTO request) {

        // On vérifie si la salle cible existe
        final Room targetRoom = roomService.getRoom(locality,request.getTargetRoomName());

        //Si elle existe, on transfert chaque equipement vers la salle saisie
        final LocalDateTime now = LocalDateTime.now();
        final String transferDetails = request.getDetails();


         return request.getEquipments().stream()
                .map((equipment)->transferSingleEquipment(equipment, targetRoom, now, transferDetails))
                .toList();

    }

    private EquipmentTransfer transferSingleEquipment(final EquipmentTransfertDTO equipmentVO, final Room targetRoom, final LocalDateTime now, final String transferDetails) {
        // On vérifie si l'equipement est présent en base
        final Equipment equipment = equipmentService.getEquipment(equipmentVO.getSerialNumber(), equipmentVO.getReferenceName());

        final Locality locality = equipment.getRoom().getLocality();

        // On vérifie si la salle à laquelle est rattachée l'equipement existe
        final Room sourceRoom = roomService.getRoom(locality.getName(), equipmentVO.getRoomName());

        // On vérifie que l'equipement est bien rattaché à la salle source en bdd
        if (!equipment.getRoom().getId().equals(sourceRoom.getId())) {
            throw new EquipmentLocationMismatchException(
                    equipmentVO.getReferenceName(),
                    equipmentVO.getRoomName(),
                    equipment.getRoom().getName()
            );
        }

        // On créé le transfert
        final EquipmentTransfer equipmentTransfer = new EquipmentTransfer();
        equipmentTransfer.setEquipment(equipment);
        equipmentTransfer.setTransferDate(now);
        equipmentTransfer.setFromRoom(equipment.getRoom().getName());
        equipmentTransfer.setToRoom(targetRoom.getName());
        equipmentTransfer.setDetails(transferDetails);

        equipmentTransferRepo.save(equipmentTransfer);

        // On met à jour l'équipement
        equipment.setRoom(targetRoom);
        equipment.setUpdatedAt(now);
        equipmentRepo.save(equipment);

        return equipmentTransfer;
    }

}

