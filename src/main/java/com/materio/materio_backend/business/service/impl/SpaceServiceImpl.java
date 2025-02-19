package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.SpaceHasEquipedZonesException;
import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.dto.Space.SpaceMapper;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.business.service.SpaceService;
import com.materio.materio_backend.jpa.entity.Space;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    SpaceRepository spaceRepo;
    @Autowired
    LocalityServiceImpl localityService;
    @Autowired
    SpaceMapper spaceMapper;


    @Override
    public void createSpace(final SpaceBO spaceBO) {
        spaceRepo.findByName(spaceBO.getName())
                .ifPresent(r -> {
                    throw new RuntimeException("La salle est déjà existante"); });

        final Locality locality = localityService.getLocalityByName(spaceBO.getLocalityName());

        final Space room = new Space();
        room.setName(spaceBO.getName());
        room.setLocality(locality);

        spaceRepo.save(room);
    }
    @Override
    public void deleteSpace(final String locality, final String spaceName) {
        final SpaceBO spaceBO = getSpace(locality, spaceName);

        boolean hasEquipment = spaceBO.getZones().stream()
                .anyMatch(zone -> !zone.getEquipments().isEmpty());

        if (hasEquipment) {
            throw new SpaceHasEquipedZonesException(spaceName);
        }
        spaceRepo.delete(spaceMapper.boToEntity(spaceBO));
    }
    @Override
    public List<SpaceBO> getSpacesByLocality(String localityName) {
        localityService.getLocalityByName(localityName);
       List<Space> spaces =  spaceRepo.findByLocality_Name(localityName);

        return spaces.stream()
                .map(space -> spaceMapper.entityToBO(space)).toList();
    }
    @Override
    public SpaceBO getSpace(String localityName, String name) {
         Space space = spaceRepo.findByNameAndLocality_Name(name, localityName)
                .orElseThrow(() -> new SpaceNotFoundException(name));
        return spaceMapper.entityToBO(space);
    }

}
