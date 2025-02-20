package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.space.DuplicateSpaceException;
import com.materio.materio_backend.business.exception.space.SpaceHasEquipedZonesException;
import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.dto.Space.SpaceMapper;
import com.materio.materio_backend.business.service.SpaceService;
import com.materio.materio_backend.jpa.entity.Space;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceRepository spaceRepo;
    @Autowired
    private LocalityServiceImpl localityService;
    @Autowired
    private SpaceMapper spaceMapper;


    @Override
    public SpaceBO createSpace(final SpaceBO spaceBO) {
        spaceRepo.findByName(spaceBO.getName())
                .ifPresent(r -> {
                    throw new DuplicateSpaceException(spaceBO.getName()); });

        final LocalityBO localityBO = localityService.getLocality(spaceBO.getLocalityName());



        final Space space = spaceRepo.findByNameAndLocality_Name(spaceBO.getName(), spaceBO.getLocalityName())
                        .orElseThrow(() -> new SpaceNotFoundException(spaceBO.getName()));

        spaceRepo.save(space);
        return spaceMapper.entityToBO(space);
    }

    @Override
    public SpaceBO updateSpace(String localityName, String spaceName, SpaceBO spaceBO) {
        Space space = spaceRepo.findByNameAndLocality_Name(spaceName, localityName)
                .orElseThrow(() -> new SpaceNotFoundException(spaceName));


        if (!spaceName.equals(spaceBO.getName())) {
            spaceRepo.findByName(spaceBO.getName())
                    .ifPresent(s -> {
                        throw new DuplicateSpaceException(spaceBO.getName());
                    });
        }

        space.setName(spaceBO.getName());


        return spaceMapper.entityToBO(spaceRepo.save(space));
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
    public Set<SpaceBO> getSpacesByLocality(String localityName) {
        localityService.getLocality(localityName);
       Set<Space> spaces =  spaceRepo.findByLocality_Name(localityName);

        return spaces.stream()
                .map(space -> spaceMapper.entityToBO(space)).collect(Collectors.toSet());
    }
    @Override
    public SpaceBO getSpace(String localityName, String name) {
         Space space = spaceRepo.findByNameAndLocality_Name(name, localityName)
                .orElseThrow(() -> new SpaceNotFoundException(name));
        return spaceMapper.entityToBO(space);
    }

}
