package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.EquipmentLocationMismatchException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.business.service.LocalityService;
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
    @Autowired
    private LocalityService localityService;

    @Override
    public List<EquipmentTransfer> processTransfer(String sourceLocality, TransferRequestDTO request) {
        // Vérification des localities
        // On vérifie que la locality source existe
        localityService.getLocalityByName(sourceLocality);
        // On vérifie que la locality cible existe
        Locality targetLocality = localityService.getLocalityByName(request.getTargetLocality());

        // On vérifie que la salle cible existe
        Room targetRoom = roomService.getRoom(request.getTargetLocality(), request.getTargetRoomName());

        // Traitement des transferts
        LocalDateTime now = LocalDateTime.now();
        return request.getEquipments().stream()
                .map(equipment -> transferSingleEquipment(
                        equipment,
                        sourceLocality,
                        targetRoom,
                        now,
                        request.getDetails()))
                .toList();
    }

    private EquipmentTransfer transferSingleEquipment(
            EquipmentTransfertDTO equipmentVO,
            String sourceLocality,
            Room targetRoom,
            LocalDateTime now,
            String transferDetails) {

        // Récupération de l'équipement
        Equipment equipment = equipmentService.getEquipment(
                equipmentVO.getSerialNumber(),
                equipmentVO.getReferenceName());

        // Vérification de la salle source
        Room sourceRoom = roomService.getRoom(sourceLocality, equipmentVO.getRoomName());

        // Vérification de la localisation actuelle
        validateEquipmentLocation(equipment, sourceRoom, equipmentVO);

        // Création du transfert
        EquipmentTransfer equipmentTransfer = new EquipmentTransfer();
        equipmentTransfer.setEquipment(equipment);
        equipmentTransfer.setTransferDate(now);
        equipmentTransfer.setFromRoom(sourceRoom.getName());
        equipmentTransfer.setToRoom(targetRoom.getName());
        equipmentTransfer.setFromLocality(sourceRoom.getLocality().getName());
        equipmentTransfer.setToLocality(targetRoom.getLocality().getName());
        equipmentTransfer.setDetails(transferDetails);

        // Sauvegarde du transfert
        equipmentTransfer = equipmentTransferRepo.save(equipmentTransfer);

        // Mise à jour de l'équipement
        equipment.setRoom(targetRoom);
        equipment.setUpdatedAt(now);
        equipmentRepo.save(equipment);

        return equipmentTransfer;
    }

    private void validateEquipmentLocation(final Equipment equipment,final Room sourceRoom,final EquipmentTransfertDTO equipmentVO) {
        if (!equipment.getRoom().getId().equals(sourceRoom.getId())) {
            throw new EquipmentLocationMismatchException(
                    equipmentVO.getReferenceName(),
                    equipmentVO.getRoomName(),
                    sourceRoom.getLocality().getName(),
                    equipment.getRoom().getName(),
                    equipment.getRoom().getLocality().getName()
            );
        }
    }
}
