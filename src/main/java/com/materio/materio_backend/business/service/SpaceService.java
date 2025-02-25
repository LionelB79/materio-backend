package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.jpa.entity.Space;

import java.util.List;
import java.util.Set;

public interface SpaceService {
    SpaceBO getSpace(String locality, String spaceName);
    SpaceBO createSpace(SpaceBO spaceBO);
    void deleteSpace(String locality, String spaceName);
    Set<SpaceBO> getSpacesByLocality(String locality);
    SpaceBO updateSpace(String localityName, String spaceName, SpaceBO spaceBO);
}
