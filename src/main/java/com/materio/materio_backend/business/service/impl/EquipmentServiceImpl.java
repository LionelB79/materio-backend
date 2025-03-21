package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.business.service.ZoneService;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.LocalityRepository;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import com.materio.materio_backend.jpa.repository.ZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private EquipmentRepository equipmentRepo;
    @Autowired
    private EquipmentRefService equipmentRefService;
    @Autowired
    private LocalityRepository localityRepository;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneMapper zoneMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private ZoneRepository zoneRepository;


    @Override
    public EquipmentBO createEquipment(final EquipmentBO equipmentBO) {
        // On vérifie l'unicité du numéro de série pour cette référence
        equipmentRepo.findByIdSerialNumberAndIdReferenceName(
                        equipmentBO.getSerialNumber(),
                        equipmentBO.getReferenceName())
                .ifPresent(e -> {
                    throw new DuplicateEquipmentException(
                            equipmentBO.getReferenceName(),
                            equipmentBO.getSerialNumber());
                });

        // On récupère récupére la zone depuis la base de données
        Zone zone = zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                        equipmentBO.getZoneName(),
                        equipmentBO.getSpaceName(),
                        equipmentBO.getLocalityName())
                .orElseThrow(() -> new ZoneNotFoundException(equipmentBO.getZoneName()));

        // Création de l'équipement
        Equipment equipment = equipmentMapper.boToEntity(equipmentBO);
        equipment.setZone(zone);

        // Mise à jour du compteur de référence
        equipmentRefService.getOrCreateReference(equipmentBO.getReferenceName());

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

        // On récupère l'équipement existant
        Equipment equipment = equipmentRepo.findByIdSerialNumberAndIdReferenceName(equipmentBO.getSerialNumber(), equipmentBO.getReferenceName())
                .orElseThrow(() -> new EquipmentNotFoundException(equipmentBO.getReferenceName()));

        Zone zone = zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(equipmentBO.getZoneName(), equipmentBO.getSpaceName(), equipmentBO.getLocalityName())
                .orElseThrow(() -> new ZoneNotFoundException(equipmentBO.getZoneName()));

        // Mise à jour uniquement des informations de base de l'équipement
        equipment.setPurchaseDate(equipmentBO.getPurchaseDate());
        equipment.setDescription(equipmentBO.getDescription());
        equipment.setMark(equipmentBO.getMark());
        equipment.setTag(equipmentBO.getTag());
        equipment.setBarcode(equipmentBO.getBarCode());
        equipment.setZone(zone);
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

        // On vérifie si la zone existe
        zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                        zoneName, spaceName, localityName)
                .orElseThrow(() -> new ZoneNotFoundException(zoneName));

        Set<Equipment> equipments = equipmentRepo.findByZoneNameAndZoneSpaceNameAndZoneSpaceLocalityName(
                zoneName, spaceName, localityName);

        return equipments.stream().map(equipment -> equipmentMapper.entityToBO(equipment)).collect(Collectors.toSet());
    }

    @Override
    public Set<EquipmentBO> getEquipmentsBySpace(String localityName, String spaceName) {
        // On vérifie si l'espace existe
        spaceRepository.findByNameAndLocality_Name(spaceName, localityName)
                .orElseThrow(() -> new SpaceNotFoundException(spaceName));

        Set<Equipment> equipments = equipmentRepo.findByZoneSpaceNameAndZoneSpaceLocalityName(
                spaceName, localityName);

        return equipments.stream()
                .map(equipmentMapper::entityToBO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EquipmentBO> getEquipmentsByLocality(String localityName) {
        // On vérifie si la localité existe
        localityRepository.findByName(localityName)
                .orElseThrow(() -> new LocalityNotFoundException(localityName));

        Set<Equipment> equipments = equipmentRepo.findByZoneSpaceLocalityName(localityName);

        return equipments.stream()
                .map(equipmentMapper::entityToBO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EquipmentBO> getAllEquipments() {
        return equipmentRepo.findAll().stream()
                .map(equipmentMapper::entityToBO)
                .collect(Collectors.toSet());
    }
}
