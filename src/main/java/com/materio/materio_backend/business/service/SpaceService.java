package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.jpa.entity.Space;

import java.util.List;

public interface SpaceService {
    SpaceBO getSpace(String locality, String spaceName);
    void createSpace(SpaceBO spaceBO);
    void deleteSpace(String locality, String spaceName);
    List<SpaceBO> getSpacesByLocality(String locality);
}
