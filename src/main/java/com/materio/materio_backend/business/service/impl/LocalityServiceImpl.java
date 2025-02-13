package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.BO.LocalityBO;
import com.materio.materio_backend.business.exception.LocalityNotFoundException;
import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.repository.LocalityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LocalityServiceImpl implements LocalityService {

    @Autowired
    private LocalityRepository localityRepo;

    @Override
    public Locality createLocality(LocalityBO localityBO) {
        localityRepo.findByName(localityBO.getName())
                .ifPresent(l -> {
                    throw new RuntimeException("La localité '\" + localityBO.getName() + \"' existe déjà");
                });
        Locality locality = new Locality();
        locality.setName(localityBO.getName());
        locality.setAddress(localityBO.getAddress());
        locality.setCp(localityBO.getCp());
        locality.setCity(localityBO.getCity());
        return localityRepo.save(locality);
    }

    @Override
    public Locality getLocalityByName(String name) {
        return localityRepo.findByName(name)
                .orElseThrow(() -> new LocalityNotFoundException(name));
    }
}
