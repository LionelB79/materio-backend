package com.materio.materio_backend.dto.Zone;

import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZoneMapper {

    @Autowired
    private final SpaceRepository spaceRepo;

    public Zone boToEntity(ZoneBO zoneBO) {
        if (zoneBO == null) return null;

        Zone entity = new Zone();
        entity.setName(zoneBO.getName());
        entity.setDescription(zoneBO.getDescription());
        entity.setSpace(zoneBO.getSpace());
        entity.setEquipments(zoneBO.getEquipments());
        entity.setCreatedAt(zoneBO.getCreatedAt());
        entity.setUpdatedAt(zoneBO.getUpdatedAt());
        return entity;
    }

    public ZoneBO entityToBO(Zone entity) {
        if (entity == null) return null;

        ZoneBO bo = new ZoneBO();
        bo.setName(entity.getName());
        bo.setDescription(entity.getDescription());
        bo.setSpace(entity.getSpace());
        bo.setSpaceId(entity.getSpace().getId());
        bo.setEquipments(entity.getEquipments());
        bo.setCreatedAt(entity.getCreatedAt());
        bo.setUpdatedAt(entity.getUpdatedAt());
        return bo;
    }

    public ZoneVO boToVO(ZoneBO bo) {
        if (bo == null) return null;

        ZoneVO vo = new ZoneVO();
        vo.setName(bo.getName());
        vo.setDescription(bo.getDescription());
        vo.setSpaceId(bo.getSpaceId());
        vo.setCreatedAt(bo.getCreatedAt());
        vo.setUpdatedAt(bo.getUpdatedAt());
        return vo;
    }

    public ZoneBO voToBO(ZoneVO vo) {
        if (vo == null) return null;

        ZoneBO bo = new ZoneBO();
        bo.setName(vo.getName());
        bo.setDescription(vo.getDescription());
        // Fetch the Space entity using spaceId
        if (vo.getSpaceId() != null) {
            bo.setSpace(spaceRepo.findById(vo.getSpaceId())
                    .orElseThrow(() -> new EntityNotFoundException("Space not found with id: " + vo.getSpaceId())));
        }

        return bo;
    }
}
