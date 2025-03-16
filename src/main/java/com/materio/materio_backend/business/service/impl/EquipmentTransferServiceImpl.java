package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.equipment.EquipmentLocationMismatchException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.transfer.TransferValidationException;
import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
import com.materio.materio_backend.business.service.*;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Transfer.*;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.EquipmentTransferRepository;
import com.materio.materio_backend.jpa.repository.ZoneRepository;
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
       private EquipmentRepository equipmentRepo;
       @Autowired
       private EquipmentTransferRepository equipmentTransferRepo;
       @Autowired
       private ZoneRepository zoneRepository;
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

               Equipment equipment = equipmentRepo.findByIdSerialNumberAndIdReferenceName(
                               equipmentToTransfer.getSerialNumber(),
                               equipmentToTransfer.getReferenceName())
                       .orElseThrow(() -> new EquipmentNotFoundException(equipmentToTransfer.getReferenceName()));

               EquipmentBO equipmentBO = equipmentMapper.entityToBO(equipment);

               validateCurrentLocation(equipmentBO, equipmentToTransfer);

               // Création et sauvegarde du transfert
               EquipmentTransfer transfer = createTransferEntity(equipmentBO, equipmentToTransfer, transferBO);
               EquipmentTransfer savedTransfer = equipmentTransferRepo.save(transfer);

               // Mise à jour de la localisation de l'équipement
               updateEquipmentLocation(equipment, transferBO);

               results.add(transferMapper.entityToBO(savedTransfer));
           }

           return results;
       }

       private void validateTargetZone(EquipmentTransferBO transferBO) {
           // Vérification de la zone cible
           zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                           transferBO.getTargetZoneName(),
                           transferBO.getTargetSpaceName(),
                           transferBO.getTargetLocalityName())
                   .orElseThrow(() -> new ZoneNotFoundException(transferBO.getTargetZoneName()));
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

       private void updateEquipmentLocation(Equipment equipment, EquipmentTransferBO transferBO) {
           // Récupérer la zone cible
           Zone targetZone = zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                           transferBO.getTargetZoneName(),
                           transferBO.getTargetSpaceName(),
                           transferBO.getTargetLocalityName())
                   .orElseThrow(() -> new ZoneNotFoundException(transferBO.getTargetZoneName()));

           // Mettre à jour directement
           equipment.setZone(targetZone);
           equipmentRepo.save(equipment);
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
                       Equipment eq = equipmentRepo.findByIdSerialNumberAndIdReferenceName(
                                       equipment.getSerialNumber(),
                                       equipment.getReferenceName())
                               .orElseThrow(() -> new EquipmentNotFoundException(equipment.getReferenceName()));

                       return eq.getZone().getSpace().getLocality().getName();
                   })
                   .collect(Collectors.toSet());

           if (sourceLocalities.size() > 1) {
               throw new TransferValidationException("Les équipements doivent provenir de la même localité");
           }
       }
   }