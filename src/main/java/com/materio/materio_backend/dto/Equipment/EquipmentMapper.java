package com.materio.materio_backend.dto.Equipment;

import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.entity.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentMapper {
@Autowired
    private ZoneMapper zoneMapper;

    public Equipment boToEntity(EquipmentBO bo) {
        if (bo == null) return null;

        Equipment entity = new Equipment();

        entity.setSerialNumber(bo.getSerialNumber());
        entity.setReferenceName(bo.getReferenceName());

        entity.setPurchaseDate(bo.getPurchaseDate());
        entity.setMark(bo.getMark());
        entity.setDescription(bo.getDescription());

        return entity;
    }

    /**
     * Convertit une entité en BO
     * Cette méthode enrichit le BO avec les informations de navigation (spaceName, zoneName, etc.)
     */
    public EquipmentBO entityToBO(Equipment entity) {
        if (entity == null) return null;

        EquipmentBO bo = new EquipmentBO();

        bo.setSerialNumber(entity.getSerialNumber());
        bo.setReferenceName(entity.getReferenceName());
        bo.setPurchaseDate(entity.getPurchaseDate());
        bo.setMark(entity.getMark());
        bo.setDescription(entity.getDescription());

        if (entity.getZone() != null) {
            bo.setZoneName(entity.getZone().getName());
            if (entity.getZone().getSpace() != null) {
                bo.setSpaceName(entity.getZone().getSpace().getName());
                if (entity.getZone().getSpace().getLocality() != null) {
                    bo.setLocalityName(entity.getZone().getSpace().getLocality().getName());
                }
            }
        }

        return bo;
    }


    public EquipmentVO boToVO(EquipmentBO bo) {
        if (bo == null) return null;

        EquipmentVO vo = new EquipmentVO();

        vo.setSerialNumber(bo.getSerialNumber());
        vo.setReferenceName(bo.getReferenceName());
        vo.setPurchaseDate(bo.getPurchaseDate());
        vo.setMark(bo.getMark());
        vo.setDescription(bo.getDescription());
        vo.setZoneName(bo.getZoneName());
        vo.setSpaceName(bo.getSpaceName());


        return vo;
    }


    public EquipmentBO voToBO(EquipmentVO vo) {
        if (vo == null) return null;

        EquipmentBO bo = new EquipmentBO();

        bo.setSerialNumber(vo.getSerialNumber());
        bo.setReferenceName(vo.getReferenceName());
        bo.setPurchaseDate(vo.getPurchaseDate());
        bo.setMark(vo.getMark());
        bo.setDescription(vo.getDescription());
        bo.setZoneName(vo.getZoneName());
        bo.setSpaceName(vo.getSpaceName());

        return bo;
    }


    public void updateEntityFromBO(Equipment entity, EquipmentBO bo) {
        if (entity == null || bo == null) return;

        entity.setPurchaseDate(bo.getPurchaseDate());
        entity.setMark(bo.getMark());
        entity.setDescription(bo.getDescription());

    }
}