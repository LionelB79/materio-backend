package com.materio.materio_backend.dto.Equipment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipmentVO {
    private Long id;
    private String referenceName;
    private String serialNumber;
    private LocalDate purchaseDate;
    private String mark;
    private String description;
    private Long zoneId;

    private String tag;
    private Integer barCode;
}