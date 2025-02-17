package com.materio.materio_backend.dto.Room;


import com.materio.materio_backend.dto.Equipment.EquipmentVO;
import com.materio.materio_backend.jpa.entity.Equipment;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RoomVO {
    private String name;
    private String localityName;
    private Set<EquipmentVO> equipments;
}
