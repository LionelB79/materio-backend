package com.materio.materio_backend.business.service;


import com.materio.materio_backend.jpa.repository.LocalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityValidationService {

    @Autowired
    private LocalityRepository localityRepo;


    public boolean isLocalityEmpty(String name) {
        return localityRepo.findByName(name)
                .map(locality -> locality.getSpaces().stream()
                        .flatMap(space -> space.getZones().stream())
                        .allMatch(zone -> zone.getEquipments().isEmpty()))
                .orElse(true);
    }
}
