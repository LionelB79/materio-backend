package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.business.exception.zone.DuplicateZoneException;
import com.materio.materio_backend.business.exception.zone.ZoneNotEmptyException;
import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
import com.materio.materio_backend.business.service.ZoneService;
import com.materio.materio_backend.dto.Space.SpaceMapper;
import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.entity.Space;
import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import com.materio.materio_backend.jpa.repository.ZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public ZoneBO createZone(ZoneBO zoneBO) {
        // Vérifie l'existence de l'espace parent
        Space space = spaceRepository.findById(zoneBO.getSpaceId())
                .orElseThrow(() -> new SpaceNotFoundException("ID: " + zoneBO.getSpaceId()));

        // Vérification de l'unicité de la zone dans cet espace
        if (zoneRepository.existsByNameAndSpaceId(zoneBO.getName(), zoneBO.getSpaceId())) {
            throw new DuplicateZoneException(zoneBO.getName());
        }

        // Conversion en entité pour la persistance
        Zone entity = zoneMapper.boToEntity(zoneBO);
        entity.setSpace(space);

        // Sauvegarde et reconversion en BO pour le retour
        Zone savedEntity = zoneRepository.save(entity);
        return zoneMapper.entityToBO(savedEntity);
    }

    @Override
    public ZoneBO updateZone(Long id, ZoneBO zoneBO) {
        // Récupération de l'entité existante
        Zone existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new ZoneNotFoundException("ID: " + id));

        // Si le nom change, vérifier l'unicité
        if (!existingZone.getName().equals(zoneBO.getName()) &&
                zoneRepository.existsByNameAndSpaceId(zoneBO.getName(), existingZone.getSpace().getId())) {
            throw new DuplicateZoneException(zoneBO.getName());
        }

        // Si l'espace change
        if (!existingZone.getSpace().getId().equals(zoneBO.getSpaceId())) {
            Space newSpace = spaceRepository.findById(zoneBO.getSpaceId())
                    .orElseThrow(() -> new SpaceNotFoundException("ID: " + zoneBO.getSpaceId()));
            existingZone.setSpace(newSpace);
        }

        // Mise à jour des champs modifiables
        zoneMapper.updateEntityFromBO(existingZone, zoneBO);

        // Sauvegarde et reconversion en BO
        Zone updatedEntity = zoneRepository.save(existingZone);
        return zoneMapper.entityToBO(updatedEntity);
    }

    @Override
    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ZoneNotFoundException("ID: " + id));

        // Vérification si la zone contient des équipements
        if (!zone.getEquipments().isEmpty()) {
            throw new ZoneNotEmptyException(zone.getName());
        }

        zoneRepository.delete(zone);
    }

    @Override
    public ZoneBO getZone(Long id) {
        Zone entity = zoneRepository.findById(id)
                .orElseThrow(() -> new ZoneNotFoundException("ID: " + id));
        return zoneMapper.entityToBO(entity);
    }

    @Override
    public Set<ZoneBO> getZonesBySpaceId(Long spaceId) {
        // Vérifier que l'espace existe
        if (!spaceRepository.existsById(spaceId)) {
            throw new SpaceNotFoundException("ID: " + spaceId);
        }

        return zoneRepository.findBySpaceId(spaceId).stream()
                .map(zoneMapper::entityToBO)
                .collect(Collectors.toSet());
    }

    @Override
    public ZoneBO getZoneByNameAndSpaceId(String zoneName, Long spaceId) {
        Zone zone = zoneRepository.findByNameAndSpaceId(zoneName, spaceId)
                .orElseThrow(() -> new ZoneNotFoundException(zoneName));
        return zoneMapper.entityToBO(zone);
    }
}