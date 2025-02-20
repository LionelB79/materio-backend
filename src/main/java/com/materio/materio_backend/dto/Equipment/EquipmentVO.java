package com.materio.materio_backend.dto.Equipment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipmentVO {
    private String referenceName;
    private String serialNumber;
    private LocalDate purchaseDate;
    private String mark;
    private String description;
    private String spaceName;
    private String zoneName;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
