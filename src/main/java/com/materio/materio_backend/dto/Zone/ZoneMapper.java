package com.materio.materio_backend.dto.Zone;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ZoneMapper {

    @Autowired
    private EquipmentMapper equipmentMapper;

    public Zone boToEntity(ZoneBO zoneBO) {
        if (zoneBO == null) return null;

        Zone entity = new Zone();
        entity.setId(zoneBO.getId());
        entity.setName(zoneBO.getName());
        entity.setDescription(zoneBO.getDescription());
        // Le space sera défini dans le service

        if (zoneBO.getEquipments() != null && !zoneBO.getEquipments().isEmpty()) {
            Set<Equipment> equipments = zoneBO.getEquipments().stream()
                    .map(equipmentMapper::boToEntity)
                    .collect(Collectors.toSet());
            entity.setEquipments(equipments);
        } else {
            entity.setEquipments(new HashSet<>());
        }

        return entity;
    }

    public ZoneBO entityToBO(Zone zone) {
        if (zone == null) return null;

        ZoneBO zoneBO = new ZoneBO();
        zoneBO.setId(zone.getId());
        zoneBO.setName(zone.getName());
        zoneBO.setDescription(zone.getDescription());

        if (zone.getSpace() != null) {
            zoneBO.setSpaceId(zone.getSpace().getId());

        }

        if (zone.getEquipments() != null) {
            Set<EquipmentBO> equipments = zone.getEquipments().stream()
                    .map(equipmentMapper::entityToBO)
                    .collect(Collectors.toSet());
            zoneBO.setEquipments(equipments);
        }

        return zoneBO;
    }

    public ZoneVO boToVO(ZoneBO zoneBO) {
        if (zoneBO == null) return null;

        ZoneVO vo = new ZoneVO();
        vo.setId(zoneBO.getId());
        vo.setName(zoneBO.getName());
        vo.setDescription(zoneBO.getDescription());
        vo.setSpaceId(zoneBO.getSpaceId());


        if (zoneBO.getEquipments() != null) {
            vo.setEquipments(new HashSet<>(zoneBO.getEquipments()));
        }

        return vo;
    }

    public ZoneBO voToBO(ZoneVO zoneVO) {
        if (zoneVO == null) return null;

        ZoneBO bo = new ZoneBO();
        bo.setId(zoneVO.getId());
        bo.setName(zoneVO.getName());
        bo.setDescription(zoneVO.getDescription());
        bo.setSpaceId(zoneVO.getSpaceId());

        if (zoneVO.getEquipments() != null) {
            bo.setEquipments(new HashSet<>(zoneVO.getEquipments()));
        }

        return bo;
    }

    public void updateEntityFromBO(Zone zone, ZoneBO zoneBO) {
        if (zone == null || zoneBO == null) return;
        zone.setName(zoneBO.getName());
        zone.setDescription(zoneBO.getDescription());
    }
}