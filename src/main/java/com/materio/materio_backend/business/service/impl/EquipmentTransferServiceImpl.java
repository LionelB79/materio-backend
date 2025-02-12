package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.EquipmentLocationMismatchException;
import com.materio.materio_backend.business.exception.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.RoomNotFoundException;
import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.EquipmentTransferRepository;
import com.materio.materio_backend.jpa.repository.RoomRepository;
import com.materio.materio_backend.view.VO.EquipementVO;
import com.materio.materio_backend.view.VO.TransferRequestVO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EquipmentTransferServiceImpl implements EquipmentTransferService {

    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private EquipmentRepository equipmentRepo;

    @Autowired
    private EquipmentTransferRepository equipmentTransferRepo;

    @Override
    public List<EquipmentTransfer> processTransfer(TransferRequestVO request) {

        // On vérifie si la salle cible existe
        Room targetRoom = roomRepo.findByName(request.getTargetRoomName())
                .orElseThrow(() -> new RoomNotFoundException(request.getTargetRoomName()));

        //Si elle existe, on transfert chaque equipement vers la salle saisie
        List<EquipmentTransfer> equipments = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        String transferDetails = request.getDetails();

        for (EquipementVO equipementVO : request.getEquipments()) {
            equipments.add(transferSingleEquipment(equipementVO, targetRoom, now, transferDetails));
        }
        /*
         return request.getEquipments().stream()
                .map((equipment)->transferSingleEquipment(equipment, targetRoom, now, transferDetails))
                .toList();
*/
        return equipments;
    }

    private EquipmentTransfer transferSingleEquipment(EquipementVO equipmentVO, Room targetRoom, LocalDateTime now, String transferDetails) {
        // On vérifie si l'equipement est présent en base
        Equipment equipment = equipmentRepo.findById(equipmentVO.getId())
                .orElseThrow(() -> new EquipmentNotFoundException(equipmentVO.getReferenceName() + " id : " + equipmentVO.getId()));

        // On vérifie si la salle à laquelle est rattachée l'equipement existe
        Room sourceRoom = roomRepo.findByName(equipmentVO.getRoomName())
                .orElseThrow(() -> new RoomNotFoundException(equipmentVO.getRoomName()));

        // On vérifie que l'equipement est bien rattaché à la salle source en bdd
        if (!equipment.getRoom().getId().equals(sourceRoom.getId())) {
            throw new EquipmentLocationMismatchException(
                    equipmentVO.getReferenceName(),
                    equipmentVO.getRoomName(),
                    equipment.getRoom().getName()
            );
        }

        // On créé le transfert
        EquipmentTransfer equipmentTransfer = new EquipmentTransfer();
        equipmentTransfer.setEquipmentName(equipment.getReferenceName());
        equipmentTransfer.setTransferDate(now);
        equipmentTransfer.setEquipmentId(equipment);
        equipmentTransfer.setFromRoom(equipment.getRoom());
        equipmentTransfer.setToRoom(targetRoom);
        equipmentTransfer.setDetails(transferDetails);

        equipmentTransferRepo.save(equipmentTransfer);

        // On met à jour l'équipement
        equipment.setRoom(targetRoom);
        equipment.setUpdatedAt(now);
        equipmentRepo.save(equipment);

        return equipmentTransfer;
    }

}

