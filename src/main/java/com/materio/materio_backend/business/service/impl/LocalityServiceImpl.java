package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.locality.DuplicateLocalityException;
import com.materio.materio_backend.business.exception.locality.LocalityNotEmptyException;
import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.repository.LocalityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class LocalityServiceImpl implements LocalityService {



    @Autowired
    private LocalityRepository localityRepo;

    @Override
    public Locality createLocality(LocalityBO localityBO) {
        localityRepo.findByName(localityBO.getName())
                .ifPresent(l -> {
                    throw new DuplicateLocalityException(localityBO.getName());
                });
        Locality locality = new Locality();
        updateLocalityFields(locality, localityBO);
        return localityRepo.save(locality);
    }

    @Override
    public Locality getLocalityByName(String name) {
        return localityRepo.findByName(name)
                .orElseThrow(() -> new LocalityNotFoundException(name));
    }

    @Override
    public List<Locality> getAllLocalities() {
        return localityRepo.findAll();
    }

    @Override
    public Locality updateLocality(String name, LocalityBO localityBO) {
        Locality locality = getLocalityByName(name);

        // Si le nom change, vérifier qu'il n'existe pas déjà
        if (!locality.getName().equals(localityBO.getName())) {
            localityRepo.findByName(localityBO.getName())
                    .ifPresent(l -> {
                        throw new DuplicateLocalityException(localityBO.getName());
                    });
        }

        updateLocalityFields(locality, localityBO);
        return localityRepo.save(locality);
    }

    @Override
    public void deleteLocality(String name) {
        Locality locality = getLocalityByName(name);

        // Vérifier si des salles contiennent des équipements
        boolean hasEquipment = locality.getRooms().stream()
                .anyMatch(room -> !room.getEquipments().isEmpty());

        if (hasEquipment) {
            throw new LocalityNotEmptyException(name);
        }

        localityRepo.delete(locality);
    }

    private void updateLocalityFields(Locality locality, LocalityBO localityBO) {
        locality.setName(localityBO.getName());
        locality.setAddress(localityBO.getAddress());
        locality.setCp(localityBO.getCp());
        locality.setCity(localityBO.getCity());
    }
}
