package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.locality.DuplicateLocalityException;
import com.materio.materio_backend.business.exception.locality.LocalityNotEmptyException;
import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.dto.Locality.LocalityMapper;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.repository.LocalityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class LocalityServiceImpl implements LocalityService {



    @Autowired
    private LocalityRepository localityRepo;
    @Autowired
    private LocalityMapper localityMapper;


    @Override
    public LocalityBO createLocality(final LocalityBO localityBO) {

        // Vérification de l'unicité du nom
        localityRepo.findByName(localityBO.getName())
                .ifPresent(l -> {
                    throw new DuplicateLocalityException(localityBO.getName());
                });

        // Conversion et sauvegarde
        Locality entity = localityMapper.boToEntity(localityBO);
        Locality savedEntity = localityRepo.save(entity);

        return localityMapper.entityToBO(savedEntity);
    }

    @Override
    public LocalityBO getLocality(final String name) {

        Locality entity = localityRepo.findByName(name)
                .orElseThrow(() -> new LocalityNotFoundException(name));

        return localityMapper.entityToBO(entity);
    }

    @Override
    public Set<LocalityBO> getAllLocalities() {

        List<Locality> entities = localityRepo.findAll();
        return entities.stream()
                .map(localityMapper::entityToBO)
                .collect(Collectors.toSet());
    }

    @Override
    public LocalityBO updateLocality(final String name, final LocalityBO localityBO) {

        // Récupération de l'entité existante
        Locality entity = localityRepo.findByName(name)
                .orElseThrow(() -> new LocalityNotFoundException(name));

        // Vérification si le nouveau nom n'existe pas déjà
        if (!name.equals(localityBO.getName())) {
            localityRepo.findByName(localityBO.getName())
                    .ifPresent(l -> {
                        throw new DuplicateLocalityException(localityBO.getName());
                    });
        }

        // Mise à jour et sauvegarde
        localityMapper.updateEntityFromBO(entity, localityBO);
        Locality updatedEntity = localityRepo.save(entity);

        return localityMapper.entityToBO(updatedEntity);
    }

    @Override
    public void deleteLocality(String localityName) {

        Locality entity = localityRepo.findByName(localityName)
                .orElseThrow(() -> new LocalityNotFoundException(localityName));

        // Vérification des équipements
        boolean hasEquipment = entity.getSpaces().stream()
                .flatMap(space -> space.getZones().stream())
                .anyMatch(zone -> !zone.getEquipments().isEmpty());

        if (hasEquipment) {
            throw new LocalityNotEmptyException(localityName);
        }

        localityRepo.delete(entity);
    }
}
