package com.materio.materio_backend.dto.Space;

import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.entity.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class SpaceMapper {
@Autowired
    private ZoneMapper zoneMapper;

    public Space boToEntity(SpaceBO spaceBO) {
        if (spaceBO == null) return null;

        Space space = new Space();
        space.setId(spaceBO.getId());
        space.setName(spaceBO.getName());

        if (spaceBO.getZones() != null && !spaceBO.getZones().isEmpty()) {
            space.setZones(spaceBO.getZones().stream()
                    .map(zoneMapper::boToEntity)
                    .collect(Collectors.toSet()));
        } else {
            space.setZones(new HashSet<>());
        }

        return space;
    }

    public SpaceBO entityToBO(Space space) {
        if (space == null) return null;

        SpaceBO spaceBO = new SpaceBO();
        spaceBO.setId(space.getId());
        spaceBO.setName(space.getName());
        spaceBO.setLocalityName(space.getLocality() != null ? space.getLocality().getName() : null);

        if (space.getZones() != null && !space.getZones().isEmpty()) {
            spaceBO.setZones(space.getZones().stream()
                    .map(zone -> zoneMapper.entityToBO(zone))
                    .collect(Collectors.toSet()));
        } else {
            spaceBO.setZones(new HashSet<>());
        }

        return spaceBO;
    }

    public SpaceVO boToVO(SpaceBO spaceBO) {
        if (spaceBO == null) return null;

        SpaceVO spaceVO = new SpaceVO();
        spaceVO.setId(spaceBO.getId());
        spaceVO.setName(spaceBO.getName());
        spaceVO.setLocalityName(spaceBO.getLocalityName());

        if (spaceBO.getZones() != null && !spaceBO.getZones().isEmpty()) {
            spaceVO.setZones(spaceBO.getZones().stream()
                    .map(zoneMapper::boToVO)
                    .collect(Collectors.toSet()));
        } else {
            spaceVO.setZones(new HashSet<>());
        }

        return spaceVO;
    }

    public SpaceBO voToBO(SpaceVO spaceVO) {
        if (spaceVO == null) return null;

        SpaceBO spaceBO = new SpaceBO();
        spaceBO.setId(spaceVO.getId());
        spaceBO.setName(spaceVO.getName());
        spaceBO.setLocalityName(spaceVO.getLocalityName());

        if (spaceVO.getZones() != null && !spaceVO.getZones().isEmpty()) {
            spaceBO.setZones(spaceVO.getZones().stream()
                    .map(zoneMapper::voToBO)
                    .collect(Collectors.toSet()));
        } else {
            spaceBO.setZones(new HashSet<>());
        }

        return spaceBO;
    }
}