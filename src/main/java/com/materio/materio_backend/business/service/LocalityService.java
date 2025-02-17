package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.jpa.entity.Locality;

import java.util.List;

public interface LocalityService {
    Locality createLocality(LocalityBO localityBO);
    Locality getLocalityByName(String name);
    Locality updateLocality(String name, LocalityBO localityBO);
    void deleteLocality(String name);
    List<Locality> getAllLocalities();

}
