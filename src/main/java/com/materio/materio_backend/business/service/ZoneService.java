package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Zone.ZoneBO;

import java.util.List;
import java.util.Set;

public interface ZoneService {
    ZoneBO createZone(ZoneBO zoneBO);
    ZoneBO updateZone(Long id, ZoneBO zoneBO);
    void deleteZone(Long id);
    ZoneBO getZone(Long id);
    Set<ZoneBO> getZonesBySpaceId(Long spaceId);

    ZoneBO getZoneByNameAndSpaceId(String zoneName, Long spaceId);
}
