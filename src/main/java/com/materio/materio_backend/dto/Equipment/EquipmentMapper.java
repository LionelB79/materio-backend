package com.materio.materio_backend.dto.Equipment;

import com.materio.materio_backend.jpa.entity.Equipment;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {

    public Equipment BOToEntity(EquipmentBO request) {
        Equipment equipment = new Equipment();
        equipment.setSerialNumber(request.getSerialNumber());
        equipment.setReferenceName(request.getReferenceName());
        equipment.setPurchaseDate(request.getPurchaseDate());
        equipment.setMark(request.getMark());
        equipment.setDescription(request.getDescription());

        return equipment;
    }

    public EquipmentVO EntityToVO(Equipment equipment) {
        EquipmentVO response = new EquipmentVO();
        response.setReferenceName(equipment.getReferenceName());
        response.setSerialNumber(equipment.getSerialNumber());
        response.setPurchaseDate(equipment.getPurchaseDate());
        response.setMark(equipment.getMark());
        response.setDescription(equipment.getDescription());
        response.setRoomName(equipment.getRoom().getName());
        response.setCreatedAt(equipment.getCreatedAt().toLocalDate());
        response.setUpdatedAt(equipment.getUpdatedAt().toLocalDate());
        return response;
    }
}
