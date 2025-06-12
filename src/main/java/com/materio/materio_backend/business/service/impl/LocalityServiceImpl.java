package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.locality.DuplicateLocalityException;
import com.materio.materio_backend.business.exception.locality.LocalityNotEmptyException;
import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.service.EntityValidationService;
import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.dto.Locality.LocalityMapper;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.repository.LocalityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EntityValidationService validationService;

    @Override
    public LocalityBO createLocality(final LocalityBO localityBO) {
        // Vérification de l'unicité du nom
        if (localityRepo.existsByName(localityBO.getName())) {
            throw new DuplicateLocalityException(localityBO.getName());
        }

        // Conversion et sauvegarde
        Locality entity = localityMapper.boToEntity(localityBO);
        Locality savedEntity = localityRepo.save(entity);

        return localityMapper.entityToBO(savedEntity);
    }

    @Override
    public LocalityBO getLocality(final Long id) {
        Locality entity = localityRepo.findById(id)
                .orElseThrow(() -> new LocalityNotFoundException("ID: " + id));

        return localityMapper.entityToBO(entity);
    }

    @Override
    public LocalityBO getLocalityByName(final String name) {
        Locality entity = localityRepo.findByName(name)
                .orElseThrow(() -> new LocalityNotFoundException(name));

        return localityMapper.entityToBO(entity);
    }

    @Override
    public Set<LocalityBO> getAllLocalities() {
        return localityRepo.findAll().stream()
                .map(localityMapper::entityToBO)
                .collect(Collectors.toSet());
    }

    @Override
    public LocalityBO updateLocality(final Long id, final LocalityBO localityBO) {
        // Récupération de l'entité existante
        Locality entity = localityRepo.findById(id)
                .orElseThrow(() -> new LocalityNotFoundException("ID: " + id));

        // Vérification si le nouveau nom n'existe pas déjà (si différent)
        if (!entity.getName().equals(localityBO.getName()) &&
                localityRepo.existsByName(localityBO.getName())) {
            throw new DuplicateLocalityException(localityBO.getName());
        }

        // Mise à jour et sauvegarde
        localityMapper.updateEntityFromBO(entity, localityBO);
        Locality updatedEntity = localityRepo.save(entity);

        return localityMapper.entityToBO(updatedEntity);
    }

    @Override
    public void deleteLocality(Long id) {
        Locality locality = localityRepo.findById(id)
                .orElseThrow(() -> new LocalityNotFoundException("ID: " + id));

        if (!validationService.isLocalityEmpty(locality.getName())) {
            throw new LocalityNotEmptyException(locality.getName());
        }

        localityRepo.delete(locality);
    }
}
