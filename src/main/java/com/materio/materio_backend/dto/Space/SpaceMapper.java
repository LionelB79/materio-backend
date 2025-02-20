package com.materio.materio_backend.dto.Space;

import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.entity.Space;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpaceMapper {
@Autowired
    private final ZoneMapper zoneMapper;

    public Space boToEntity(SpaceBO bo) {
        if (bo == null) return null;

        Space entity = new Space();
        updateEntityFromBO(entity, bo);
        return entity;
    }

    public SpaceBO entityToBO(Space entity) {
        if (entity == null) return null;

        SpaceBO bo = new SpaceBO();
        bo.setId(entity.getId());
        bo.setName(entity.getName());

        if (entity.getLocality() != null) {
            bo.setLocalityName(entity.getLocality().getName());
        }

        if (entity.getZones() != null) {
            Set<ZoneBO> zones = entity.getZones().stream()
                    .map(zoneMapper::entityToBO)
                    .collect(Collectors.toSet());
            bo.setZones(zones);
        }

        return bo;
    }

    public SpaceVO boToVO(SpaceBO bo) {
        if (bo == null) return null;

        SpaceVO vo = new SpaceVO();
        vo.setId(bo.getId());
        vo.setName(bo.getName());
        vo.setLocalityName(bo.getLocalityName());

        // Mapping des zones
        if (bo.getZones() != null) {
            vo.setZones(bo.getZones().stream()
                    .map(zoneMapper::boToVO)
                    .collect(Collectors.toSet()));
        } else {
            vo.setZones(new HashSet<>());
        }

        return vo;
    }

    public SpaceBO voToBO(SpaceVO vo) {
        if (vo == null) return null;

        SpaceBO bo = new SpaceBO();
        bo.setId(vo.getId());
        bo.setName(vo.getName());
        bo.setLocalityName(vo.getLocalityName());

        // Mapping des zones
        if (vo.getZones() != null) {
            bo.setZones(vo.getZones().stream()
                    .map(zoneMapper::voToBO)
                    .collect(Collectors.toSet()));
        } else {
            bo.setZones(new HashSet<>());
        }

        return bo;
    }

    public Set<SpaceVO> boSetToVOSet(Set<SpaceBO> bos) {
        if (bos == null) return null;
        return bos.stream()
                .map(this::boToVO)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromBO(Space entity, SpaceBO bo) {
        if (entity == null || bo == null) return;

        entity.setId(bo.getId());
        entity.setName(bo.getName());

    }
}