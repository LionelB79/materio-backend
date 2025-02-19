package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.service.ZoneService;
import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import com.materio.materio_backend.jpa.repository.ZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ZoneServiceImpl implements ZoneService {


    @Autowired
    private  ZoneRepository zoneRepository;
    @Autowired
    private ZoneMapper zoneMapper;
    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    @Transactional
    public ZoneBO createZone(final ZoneBO zoneBO) {


        return zoneMapper.entityToBO(zone);
    }

    @Override
    @Transactional
    public ZoneBO updateZone(final ZoneBO zoneBO) {

        return zoneMapper.entityToBO(zone);
    }

    @Override
    @Transactional
    public void deleteZone( String localityName, String spaceName, String zoneName) {


    }

    @Override
    @Transactional
    public ZoneBO getZone(String localityName, String spaceName,
                               String zoneName) {


        return zoneMapper.entityToBO(getZone(localityName, spaceName, zoneName));
    }

    @Override
    @Transactional
    public List<ZoneBO> getZones(String localityName, String spaceName) {

        return zoneRepository.findBySpaceNameAndSpaceLocalityName(spaceName, localityName)
                .stream()
                .map(zoneMapper::entityToBO)
                .toList();
    }

}