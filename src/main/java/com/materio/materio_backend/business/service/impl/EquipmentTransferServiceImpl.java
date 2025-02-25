package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.equipment.EquipmentLocationMismatchException;
import com.materio.materio_backend.business.exception.transfer.TransferValidationException;
import com.materio.materio_backend.business.service.*;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Transfer.*;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.EquipmentTransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
       private ZoneService zoneService;
       @Autowired
       private EquipmentMapper equipmentMapper;
       @Autowired
       private EquipmentTransferMapper transferMapper;

       @Override
       public Set<EquipmentTransferBO> processTransfer(EquipmentTransferBO transferBO) {
           // On vérifie si la zone cible existe
           validateTargetZone(transferBO);

           // Vérification que tous les équipements sont de la même localité source
           validateSameSourceLocality(transferBO);

           Set<EquipmentTransferBO> results = new HashSet<>();


           for (EquipmentToTransfer equipmentToTransfer : transferBO.getEquipments()) {

               EquipmentBO equipment = equipmentService.getEquipment(
                       equipmentToTransfer.getSerialNumber(),
                       equipmentToTransfer.getReferenceName());

               validateCurrentLocation(equipment, equipmentToTransfer);

               // Création et sauvegarde du transfert
               EquipmentTransfer transfer = createTransferEntity(equipment, equipmentToTransfer, transferBO);
               EquipmentTransfer savedTransfer = equipmentTransferRepo.save(transfer);

               // Mise à jour de la localisation de l'équipement
               updateEquipmentLocation(equipment, transferBO);

               results.add(transferMapper.entityToBO(savedTransfer));
           }

           return results;
       }

       private void validateTargetZone(EquipmentTransferBO transferBO) {
           // Vérification de la zone cible
           zoneService.getZone(
                   transferBO.getTargetLocalityName(),
                   transferBO.getTargetSpaceName(),
                   transferBO.getTargetZoneName());
       }

       private void validateCurrentLocation(EquipmentBO equipment, EquipmentToTransfer equipmentToTransfer) {
           boolean locationMismatch = !equipment.getZoneName().equals(equipmentToTransfer.getSourceZoneName()) ||
                   !equipment.getSpaceName().equals(equipmentToTransfer.getSourceSpaceName());

           if (locationMismatch) {
               throw new EquipmentLocationMismatchException(
                       equipmentToTransfer.getReferenceName(),
                       equipment.getZoneName(),
                       equipment.getSpaceName(),
                       equipment.getLocalityName(),
                       equipmentToTransfer.getSourceZoneName(),
                       equipmentToTransfer.getSourceSpaceName(),
                       equipment.getLocalityName()); // Même localité source
           }
       }

       private EquipmentTransfer createTransferEntity(EquipmentBO equipment, EquipmentToTransfer equipmentToTransfer, EquipmentTransferBO transferBO) {
           EquipmentTransfer transfer = new EquipmentTransfer();
           transfer.setEquipment(equipmentMapper.boToEntity(equipment));
           transfer.setTransferDate(LocalDateTime.now());

           // Source
           transfer.setFromZone(equipmentToTransfer.getSourceZoneName());
           transfer.setFromSpace(equipmentToTransfer.getSourceSpaceName());
           transfer.setFromLocality(equipment.getLocalityName());

           // Destination
           transfer.setToZone(transferBO.getTargetZoneName());
           transfer.setToSpace(transferBO.getTargetSpaceName());
           transfer.setToLocality(transferBO.getTargetLocalityName());

           transfer.setDetails(transferBO.getDetails());

           return transfer;
       }

       private void updateEquipmentLocation(EquipmentBO equipment, EquipmentTransferBO transferBO) {
           equipment.setZoneName(transferBO.getTargetZoneName());
           equipment.setSpaceName(transferBO.getTargetSpaceName());
           equipment.setLocalityName(transferBO.getTargetLocalityName());

           equipmentService.updateEquipment(equipment);
       }

       @Override
       public Set<EquipmentTransferBO> getTransferHistory(String referenceName, String serialNumber) {
           return equipmentTransferRepo
                   .findByEquipmentIdReferenceNameAndEquipmentIdSerialNumber(referenceName, serialNumber)
                   .stream()
                   .map(transferMapper::entityToBO)
                   .collect(Collectors.toSet());
       }

       private void validateSameSourceLocality(EquipmentTransferBO transferBO) {
           // Récupérer tous les équipements et vérifier qu'ils sont de la même localité
           Set<String> sourceLocalities = transferBO.getEquipments().stream()
                   .map(equipment -> {
                       EquipmentBO eq = equipmentService.getEquipment(
                               equipment.getSerialNumber(),
                               equipment.getReferenceName());
                       return eq.getLocalityName();
                   })
                   .collect(Collectors.toSet());

           if (sourceLocalities.size() > 1) {
               throw new TransferValidationException("Les équipements doivent provenir de la même localité");
           }
       }
   }