package com.materio.materio_backend.dto.Transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentTransferBO {
    private String referenceName;
    private String serialNumber;

    // Informations de source
    private String sourceZoneName;
    private String sourceSpaceName;
    private String sourceLocalityName;

    // Informations de destination
    private String targetZoneName;
    private String targetSpaceName;
    private String targetLocalityName;

    private LocalDateTime transferDate;
    private String details;
}
