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
    private  EquipmentMapper equipmentMapper;


    public Zone boToEntity(ZoneBO zoneBO) {
        if (zoneBO == null) return null;

        Zone entity = new Zone();
        entity.setName(zoneBO.getName());
        entity.setDescription(zoneBO.getDescription());

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

            zoneBO.setName(zone.getName());
            zoneBO.setDescription(zone.getDescription());

            if (zone.getSpace() != null) {
                zoneBO.setSpaceName(zone.getSpace().getName());
                if (zone.getSpace().getLocality() != null) {
                    zoneBO.setLocalityName(zone.getSpace().getLocality().getName());
                }
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

            vo.setName(zoneBO.getName());
            vo.setDescription(zoneBO.getDescription());
            vo.setSpaceName(zoneBO.getSpaceName());
            vo.setLocalityName(zoneBO.getLocalityName());

            if (zoneBO.getEquipments() != null) {
                vo.setEquipments(new HashSet<>(zoneBO.getEquipments())); // Les EquipmentBO sont les mêmes
            }

            return vo;
        }

        public ZoneBO voToBO(ZoneVO zoneVO) {
            if (zoneVO == null) return null;

            ZoneBO bo = new ZoneBO();

            bo.setName(zoneVO.getName());
            bo.setDescription(zoneVO.getDescription());
            bo.setSpaceName(zoneVO.getSpaceName());
            bo.setLocalityName(zoneVO.getLocalityName());

            if (zoneVO.getEquipments() != null) {
                bo.setEquipments(new HashSet<>(zoneVO.getEquipments()));
            }

            return bo;
        }


        public void updateEntityFromBO(Zone zone, ZoneBO zoneBO) {
            if (zone == null || zoneBO == null) return;

            // Mise à jour uniquement des champs modifiables
            zone.setName(zoneBO.getName());
            zone.setDescription(zoneBO.getDescription());

        }
    }