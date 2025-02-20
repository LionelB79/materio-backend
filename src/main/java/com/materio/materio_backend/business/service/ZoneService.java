package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Zone.ZoneBO;

import java.util.List;
import java.util.Set;

public interface ZoneService {
    ZoneBO createZone(ZoneBO zoneBO);
    ZoneBO updateZone(ZoneBO zoneBO);
    void deleteZone(String localityName, String spaceName, String zoneName);
    ZoneBO getZone(String localityName, String spaceName, String zoneName);
    Set<ZoneBO> getZones(String localityName, String spaceName);
}
