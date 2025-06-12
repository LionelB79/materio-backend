package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Locality.LocalityBO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LocalityService {
    LocalityBO createLocality(LocalityBO localityBO);
    LocalityBO getLocality(Long id);
    LocalityBO getLocalityByName(String name);
    LocalityBO updateLocality(Long id, LocalityBO localityBO);
    void deleteLocality(Long id);
    Set<LocalityBO> getAllLocalities();
}
