package com.materio.materio_backend.dto.Space;


import com.materio.materio_backend.dto.Equipment.EquipmentVO;

import com.materio.materio_backend.dto.Zone.ZoneVO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SpaceVO {
    private Long id;
    private String name;
    private String localityName;
    private Set<ZoneVO> zones = new HashSet<>();
}
