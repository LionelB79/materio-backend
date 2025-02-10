package com.materio.materio_backend.business.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentBO {
    private String referenceName;
    private String mark;
    private String description;
    private String roomName;
}
