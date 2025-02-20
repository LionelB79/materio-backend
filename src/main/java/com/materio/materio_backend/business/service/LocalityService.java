package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Locality.LocalityBO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LocalityService {
    LocalityBO createLocality(LocalityBO localityBO);
    LocalityBO getLocality(String name);
    LocalityBO updateLocality(String name, LocalityBO localityBO);
    void deleteLocality(String name);
    Set<LocalityBO> getAllLocalities();

}
