package com.materio.materio_backend.dto.Equipment;

import com.materio.materio_backend.jpa.entity.Equipment;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public Equipment boToEntity(EquipmentBO bo) {
        if (bo == null) return null;

        Equipment entity = new Equipment();
        entity.setSerialNumber(bo.getSerialNumber());
        entity.setReferenceName(bo.getReferenceName());
        entity.setPurchaseDate(bo.getPurchaseDate());
        entity.setMark(bo.getMark());
        entity.setDescription(bo.getDescription());
        entity.setTag(bo.getTag());
        entity.setBarcode(bo.getBarCode());
        return entity;
    }

    public EquipmentBO entityToBO(Equipment entity) {
        if (entity == null) return null;

        EquipmentBO bo = new EquipmentBO();
        bo.setSerialNumber(entity.getSerialNumber());
        bo.setReferenceName(entity.getReferenceName());
        bo.setPurchaseDate(entity.getPurchaseDate());
        bo.setMark(entity.getMark());
        bo.setDescription(entity.getDescription());
        bo.setTag(entity.getTag());
        bo.setBarCode(entity.getBarcode());

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
        vo.setLocalityName(bo.getLocalityName());
        vo.setTag(bo.getTag());
        vo.setBarCode(bo.getBarCode());

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
        bo.setLocalityName(vo.getLocalityName());
        bo.setTag(vo.getTag());
        bo.setBarCode(vo.getBarCode());

        return bo;
    }

    public void updateEntityFromBO(Equipment entity, EquipmentBO bo) {
        if (entity == null || bo == null) return;

        entity.setPurchaseDate(bo.getPurchaseDate());
        entity.setMark(bo.getMark());
        entity.setDescription(bo.getDescription());
        entity.setTag(bo.getTag());
        entity.setBarcode(bo.getBarCode());
    }
}