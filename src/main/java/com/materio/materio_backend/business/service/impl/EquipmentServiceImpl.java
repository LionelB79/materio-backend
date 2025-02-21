package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.service.*;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.entity.Equipment;

import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private EquipmentRepository equipmentRepo;
    @Autowired
    private EquipmentRefService equipmentRefService;
    @Autowired
    private LocalityService localityService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneMapper zoneMapper;
    @Autowired private EquipmentMapper equipmentMapper;


    @Override
    public EquipmentBO createEquipment(final EquipmentBO equipmentBO) {
        // Récupération de la zone initiale pour l'équipement
        ZoneBO zoneBO = zoneService.getZone(
                equipmentBO.getLocalityName(),
                equipmentBO.getSpaceName(),
                equipmentBO.getZoneName());

        // Création de l'équipement
        Equipment equipment = equipmentMapper.boToEntity(equipmentBO);

        // Association de la zone
        Zone zone = zoneMapper.boToEntity(zoneBO);
        equipment.setZone(zone);

        // Sauvegarde
        Equipment savedEquipment = equipmentRepo.save(equipment);
        return equipmentMapper.entityToBO(savedEquipment);
    }

    @Override
    public EquipmentBO getEquipment(final String serialNumber, final String referenceName) {
         Equipment equipment = equipmentRepo.findByIdSerialNumberAndIdReferenceName(serialNumber, referenceName)
                .orElseThrow(() -> new EquipmentNotFoundException(referenceName));

        return equipmentMapper.entityToBO(equipment);
    }

    @Override
    public EquipmentBO updateEquipment(final EquipmentBO equipmentBO) {

        // Récupération de l'équipement existant
        Equipment equipment = equipmentRepo.findByIdSerialNumberAndIdReferenceName(equipmentBO.getSerialNumber(), equipmentBO.getReferenceName())
                .orElseThrow(() -> new EquipmentNotFoundException(equipmentBO.getReferenceName()));

        // Mise à jour uniquement des informations de base de l'équipement
        equipment.setPurchaseDate(equipmentBO.getPurchaseDate());
        equipment.setDescription(equipmentBO.getDescription());
        equipment.setMark(equipmentBO.getMark());
        equipmentRepo.save(equipment);

        return equipmentMapper.entityToBO(equipment);
    }

    @Override
    public void deleteEquipment(final String serialNumber, final String referenceName) {

        Equipment equipment = equipmentRepo.findByIdSerialNumberAndIdReferenceName(serialNumber, referenceName)
                .orElseThrow(() -> new EquipmentNotFoundException(referenceName));

        // Décrémentation du compteur de référence
        equipmentRefService.decrementQuantity(referenceName);

        equipmentRepo.delete(equipment);
    }

    @Override
    public Set<EquipmentBO> getEquipmentsByZone(
            String localityName,
            String spaceName,
            String zoneName) {

        // Vérification de l'existence de la zone
        zoneService.getZone(localityName, spaceName, zoneName);

       Set<Equipment> equipments =  equipmentRepo.findByZoneNameAndZoneSpaceNameAndZoneSpaceLocalityName(
                zoneName, spaceName, localityName);

        return equipments.stream().map(equipment -> equipmentMapper.entityToBO(equipment)).collect(Collectors.toSet());
    }
}

