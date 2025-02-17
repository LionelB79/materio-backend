package com.materio.materio_backend.dto.RoomDTO;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class RoomVO {
    private String name;
    private String localityName;
    private List<EquipmentBO> equipments;
}
