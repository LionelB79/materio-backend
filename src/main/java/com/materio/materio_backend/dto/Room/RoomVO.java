package com.materio.materio_backend.dto.Room;


import com.materio.materio_backend.dto.Equipment.EquipmentVO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class RoomVO {
    @NotBlank(message = "Le nom de la salle est obligatoire")
    private String name;

    @NotBlank(message = "La localité de la salle est obligatoire")
    private String localityName;
    private Set<EquipmentVO> equipments;
}
