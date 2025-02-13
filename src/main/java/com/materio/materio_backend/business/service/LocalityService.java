package com.materio.materio_backend.business.service;

import com.materio.materio_backend.business.BO.LocalityBO;
import com.materio.materio_backend.jpa.entity.Locality;

public interface LocalityService {
    Locality createLocality(LocalityBO localityBO);
    Locality getLocalityByName(String name);
}
