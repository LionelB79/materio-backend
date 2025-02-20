package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.EquipmentLocationMismatchException;
import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.service.*;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Transfer.*;
import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Space;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.EquipmentTransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class EquipmentTransferServiceImpl implements EquipmentTransferService {

    @Autowired
    private SpaceService spaceService;
    @Autowired
    private EquipmentRepository equipmentRepo;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentTransferRepository equipmentTransferRepo;
    @Autowired
    private LocalityService localityService;
    @Autowired private EquipmentMapper equipmentMapper;
    @Autowired private ZoneService zoneService;
    @Autowired private EquipmentTransferMapper transferMapper;

    @Override
    public EquipmentTransferBO processTransfer(EquipmentTransferBO transferBO) {


        // Validation des zones source et cible
        validateZones(transferBO);

        // Récupération et validation de l'équipement
        EquipmentBO equipment = equipmentService.getEquipment(
                transferBO.getSerialNumber(),
                transferBO.getReferenceName());

        validateCurrentLocation(equipment, transferBO);

        // Création et sauvegarde du transfert
        EquipmentTransfer transfer = createTransferEntity(equipment, transferBO);
        EquipmentTransfer savedTransfer = equipmentTransferRepo.save(transfer);

        // Mise à jour de la localisation de l'équipement
        updateEquipmentLocation(equipment, transferBO);

        return transferMapper.entityToBO(savedTransfer);
    }

    @Override
    public List<EquipmentTransferBO> getTransferHistory(String referenceName, String serialNumber) {


        return equipmentTransferRepo
                .findByEquipmentIdReferenceNameAndEquipmentIdSerialNumber(referenceName, serialNumber)
                .stream()
                .map(transferMapper::entityToBO)
                .collect(Collectors.toList());
    }

    private void validateZones(EquipmentTransferBO transferBO) {
        // Vérification de la zone source
        zoneService.getZone(
                transferBO.getSourceLocalityName(),
                transferBO.getSourceSpaceName(),
                transferBO.getSourceZoneName());

        // Vérification de la zone cible
        zoneService.getZone(
                transferBO.getTargetLocalityName(),
                transferBO.getTargetSpaceName(),
                transferBO.getTargetZoneName());
    }

    private void validateCurrentLocation(EquipmentBO equipment, EquipmentTransferBO transferBO) {
        boolean locationMismatch = !equipment.getZoneName().equals(transferBO.getSourceZoneName()) ||
                !equipment.getSpaceName().equals(transferBO.getSourceSpaceName()) ||
                !equipment.getLocalityName().equals(transferBO.getSourceLocalityName());

        if (locationMismatch) {
            throw new EquipmentLocationMismatchException(
                    transferBO.getReferenceName(),
                    equipment.getZoneName(),
                    equipment.getSpaceName(),
                    equipment.getLocalityName(),
                    transferBO.getSourceZoneName(),
                    transferBO.getSourceSpaceName(),
                    transferBO.getSourceLocalityName());
        }
    }

    private EquipmentTransfer createTransferEntity(EquipmentBO equipment, EquipmentTransferBO transferBO) {
        EquipmentTransfer transfer = new EquipmentTransfer();
        transfer.setEquipment(equipmentMapper.boToEntity(equipment));
        transfer.setTransferDate(LocalDateTime.now());

        // Source
        transfer.setFromZone(transferBO.getSourceZoneName());
        transfer.setFromSpace(transferBO.getSourceSpaceName());
        transfer.setFromLocality(transferBO.getSourceLocalityName());

        // Destination
        transfer.setToZone(transferBO.getTargetZoneName());
        transfer.setToSpace(transferBO.getTargetSpaceName());
        transfer.setToLocality(transferBO.getTargetLocalityName());

        transfer.setDetails(transferBO.getDetails());

        return transfer;
    }

    private void updateEquipmentLocation(EquipmentBO equipment, EquipmentTransferBO transferBO) {
        ZoneBO targetZone = zoneService.getZone(
                transferBO.getTargetLocalityName(),
                transferBO.getTargetSpaceName(),
                transferBO.getTargetZoneName());

        equipment.setZoneName(targetZone.getName());
        equipment.setSpaceName(targetZone.getSpaceName());
        equipment.setLocalityName(targetZone.getLocalityName());

        equipmentService.updateEquipment(equipment);
    }
}