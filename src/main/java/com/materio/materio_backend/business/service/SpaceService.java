package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.jpa.entity.Space;

import java.util.List;
import java.util.Set;

public interface SpaceService {
    SpaceBO getSpace(Long id);
    SpaceBO createSpace(SpaceBO spaceBO);
    SpaceBO updateSpace(Long id, SpaceBO spaceBO);
    void deleteSpace(Long id);
    Set<SpaceBO> getSpacesByLocalityId(Long localityId);
    // Méthode de compatibilité si nécessaire
    SpaceBO getSpaceByNameAndLocalityId(Long localityId, String spaceName);
}
