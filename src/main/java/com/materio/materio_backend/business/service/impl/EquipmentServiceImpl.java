package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
import com.materio.materio_backend.business.service.EquipmentRefService;
import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
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
    private EquipmentMapper equipmentMapper;
    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public EquipmentBO createEquipment(final EquipmentBO equipmentBO) {
        // Vérification de l'unicité
        if (equipmentRepo.existsBySerialNumberAndReferenceName(
                equipmentBO.getSerialNumber(),
                equipmentBO.getReferenceName())) {
            throw new DuplicateEquipmentException(
                    equipmentBO.getReferenceName(),
                    equipmentBO.getSerialNumber());
        }

        // On récupère la zone par ID
        Zone zone = zoneRepository.findById(equipmentBO.getZoneId())
                .orElseThrow(() -> new ZoneNotFoundException("ID: " + equipmentBO.getZoneId()));

        // Création de l'équipement
        Equipment equipment = equipmentMapper.boToEntity(equipmentBO);
        equipment.setZone(zone);

        // Mise à jour du compteur de référence
        equipmentRefService.getOrCreateReference(equipmentBO.getReferenceName());

        // Sauvegarde
        Equipment savedEquipment = equipmentRepo.save(equipment);

        // On retourne l'équipement avec l'ID de zone
        return equipmentMapper.entityToBO(savedEquipment);
    }

    @Override
    public EquipmentBO getEquipment(final Long id) {
        Equipment equipment = equipmentRepo.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("ID: " + id));

        return equipmentMapper.entityToBO(equipment);
    }

    @Override
    public EquipmentBO getEquipmentBySerialAndReference(final String serialNumber, final String referenceName) {
        Equipment equipment = equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, referenceName)
                .orElseThrow(() -> new EquipmentNotFoundException(referenceName + " - " + serialNumber));

        return equipmentMapper.entityToBO(equipment);
    }

    @Override
    public EquipmentBO updateEquipment(final Long id, final EquipmentBO equipmentBO) {
        // On récupère l'équipement existant
        Equipment equipment = equipmentRepo.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("ID: " + id));

        // Si zoneId est fourni, on met à jour la zone
        if (equipmentBO.getZoneId() != null) {
            Zone zone = zoneRepository.findById(equipmentBO.getZoneId())
                    .orElseThrow(() -> new ZoneNotFoundException("ID: " + equipmentBO.getZoneId()));
            equipment.setZone(zone);
        }

        // Mise à jour des informations de base de l'équipement
        equipment.setPurchaseDate(equipmentBO.getPurchaseDate());
        equipment.setDescription(equipmentBO.getDescription());
        equipment.setMark(equipmentBO.getMark());
        equipment.setTag(equipmentBO.getTag());
        equipment.setBarcode(equipmentBO.getBarCode());

        equipmentRepo.save(equipment);

        return equipmentMapper.entityToBO(equipment);
    }

    @Override
    public void deleteEquipment(final Long id) {
        Equipment equipment = equipmentRepo.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("ID: " + id));

        // Décrémentation du compteur de référence
        equipmentRefService.decrementQuantity(equipment.getReferenceName());

        equipmentRepo.delete(equipment);
    }

    @Override
    public void deleteEquipmentBySerialAndReference(final String serialNumber, final String referenceName) {
        Equipment equipment = equipmentRepo.findBySerialNumberAndReferenceName(serialNumber, referenceName)
                .orElseThrow(() -> new EquipmentNotFoundException(referenceName + " - " + serialNumber));

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

        return equipments.stream()
                .map(equipment -> equipmentMapper.entityToBO(equipment))
                .collect(Collectors.toSet());
    }
    @Override
    public Set<EquipmentBO> getEquipmentsByZoneId(Long zoneId) {
        // Vérifier que la zone existe
        if (!zoneRepository.existsById(zoneId)) {
            throw new ZoneNotFoundException("ID: " + zoneId);
        }

        Set<Equipment> equipments = equipmentRepo.findByZoneId(zoneId);

        return equipments.stream()
                .map(equipmentMapper::entityToBO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EquipmentBO> getEquipmentsByLocalityId(Long localityId) {
        // Vérifier que la localité existe
        if (!localityRepository.existsById(localityId)) {
            throw new LocalityNotFoundException("ID: " + localityId);
        }

        Set<Equipment> equipments = equipmentRepo.findByZoneSpaceLocalityId(localityId);

        return equipments.stream()
                .map(equipmentMapper::entityToBO)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<EquipmentBO> getEquipmentsBySpaceId(Long spaceId) {
        // Vérifier que l'espace existe
        if (!spaceRepository.existsById(spaceId)) {
            throw new SpaceNotFoundException("ID: " + spaceId);
        }

        Set<Equipment> equipments = equipmentRepo.findByZoneSpaceId(spaceId);

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