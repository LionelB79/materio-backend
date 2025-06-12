package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.exception.space.DuplicateSpaceException;
import com.materio.materio_backend.business.exception.space.SpaceHasEquipedZonesException;
import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.business.service.SpaceService;
import com.materio.materio_backend.dto.Locality.LocalityMapper;
import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.dto.Space.SpaceMapper;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Space;
import com.materio.materio_backend.jpa.repository.LocalityRepository;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceRepository spaceRepo;

    @Autowired
    private SpaceMapper spaceMapper;

    @Autowired
    private LocalityRepository localityRepository;

    @Override
    public SpaceBO createSpace(final SpaceBO spaceBO) {
        // On récupère directement l'entité Locality depuis le repository
        Locality locality = localityRepository.findById(spaceBO.getLocalityId())
                .orElseThrow(() -> new LocalityNotFoundException("ID: " + spaceBO.getLocalityId()));

        // On vérifie que l'espace n'existe pas déjà pour cette localité
        if (spaceRepo.existsByNameAndLocalityId(spaceBO.getName(), spaceBO.getLocalityId())) {
            throw new DuplicateSpaceException(spaceBO.getName());
        }

        // Création et configuration du nouvel espace
        Space space = spaceMapper.boToEntity(spaceBO);
        space.setLocality(locality);

        Space savedSpace = spaceRepo.save(space);
        return spaceMapper.entityToBO(savedSpace);
    }

    @Override
    public SpaceBO getSpace(Long id) {
        Space space = spaceRepo.findById(id)
                .orElseThrow(() -> new SpaceNotFoundException("ID: " + id));
        return spaceMapper.entityToBO(space);
    }

    @Override
    public SpaceBO updateSpace(Long id, SpaceBO spaceBO) {
        Space space = spaceRepo.findById(id)
                .orElseThrow(() -> new SpaceNotFoundException("ID: " + id));

        // Si le nom change, vérifier l'unicité
        if (!space.getName().equals(spaceBO.getName()) &&
                spaceRepo.existsByNameAndLocalityId(spaceBO.getName(), space.getLocality().getId())) {
            throw new DuplicateSpaceException(spaceBO.getName());
        }

        // Si la localité change
        if (!space.getLocality().getId().equals(spaceBO.getLocalityId())) {
            Locality newLocality = localityRepository.findById(spaceBO.getLocalityId())
                    .orElseThrow(() -> new LocalityNotFoundException("ID: " + spaceBO.getLocalityId()));
            space.setLocality(newLocality);
        }

        space.setName(spaceBO.getName());
        Space savedSpace = spaceRepo.save(space);
        return spaceMapper.entityToBO(savedSpace);
    }

    @Override
    public void deleteSpace(final Long id) {
        Space space = spaceRepo.findById(id)
                .orElseThrow(() -> new SpaceNotFoundException("ID: " + id));

        boolean hasEquipment = space.getZones().stream()
                .anyMatch(zone -> !zone.getEquipments().isEmpty());

        if (hasEquipment) {
            throw new SpaceHasEquipedZonesException(space.getName());
        }

        spaceRepo.delete(space);
    }

    @Override
    public Set<SpaceBO> getSpacesByLocalityId(Long localityId) {
        // Vérifier que la localité existe
        if (!localityRepository.existsById(localityId)) {
            throw new LocalityNotFoundException("ID: " + localityId);
        }

        Set<Space> spaces = spaceRepo.findByLocalityId(localityId);
        return spaces.stream()
                .map(space -> spaceMapper.entityToBO(space))
                .collect(Collectors.toSet());
    }

    @Override
    public SpaceBO getSpaceByNameAndLocalityId(Long localityId, String spaceName) {
        Space space = spaceRepo.findByNameAndLocalityId(spaceName, localityId)
                .orElseThrow(() -> new SpaceNotFoundException(spaceName));
        return spaceMapper.entityToBO(space);
    }
}
