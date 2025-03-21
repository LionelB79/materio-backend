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
    private SpaceMapper spaceMapper;
    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public ZoneBO createZone(ZoneBO zoneBO) {
        // Vérifie l'existence de l'espace parent et récupère l'entité Space
        Space space = spaceRepository.findByNameAndLocality_Name(
                        zoneBO.getSpaceName(),
                        zoneBO.getLocalityName())
                .orElseThrow(() -> new SpaceNotFoundException(zoneBO.getSpaceName()));

        // Vérification de l'unicité de la zone dans cet espace
        zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                        zoneBO.getName(),
                        zoneBO.getSpaceName(),
                        zoneBO.getLocalityName())
                .ifPresent(z -> {
                    throw new DuplicateZoneException(zoneBO.getName());
                });

        // Conversion en entité pour la persistance
        Zone entity = zoneMapper.boToEntity(zoneBO);

        // Association avec l'espace existant
        entity.setSpace(space);

        // Sauvegarde et reconversion en BO pour le retour
        Zone savedEntity = zoneRepository.save(entity);
        return zoneMapper.entityToBO(savedEntity);
    }

    @Override
    public ZoneBO updateZone(ZoneBO zoneBO) {

        // Récupération de l'entité existante
        Zone existingZone = zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                zoneBO.getName(),
                zoneBO.getSpaceName(),
                zoneBO.getLocalityName()
        ).orElseThrow(() -> new ZoneNotFoundException(zoneBO.getName()));

        // Mise à jour des champs modifiables
        zoneMapper.updateEntityFromBO(existingZone, zoneBO);

        // Sauvegarde et reconversion en BO
        Zone updatedEntity = zoneRepository.save(existingZone);

        return zoneMapper.entityToBO(updatedEntity);
    }

    @Override
    public void deleteZone(String localityName, String spaceName, String zoneName) {


        Zone zone = zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                zoneName, spaceName, localityName
        ).orElseThrow(() -> new ZoneNotFoundException(zoneName));

        // Vérification si la zone contient des équipements
        if (!zone.getEquipments().isEmpty()) {
            throw new ZoneNotEmptyException(zoneName);
        }

        zoneRepository.delete(zone);
    }

    @Override
    public ZoneBO getZone(String localityName, String spaceName, String zoneName) {

        Zone entity = zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
                zoneName, spaceName, localityName
        ).orElseThrow(() -> new ZoneNotFoundException(zoneName));

        return zoneMapper.entityToBO(entity);
    }

    @Override
    public Set<ZoneBO> getZones(String localityName, String spaceName) {

        List<Zone> entities = zoneRepository.findBySpaceNameAndSpaceLocalityName(
                spaceName, localityName
        );

        return entities.stream()
                .map(zoneMapper::entityToBO)
                .collect(Collectors.toSet());
    }

}