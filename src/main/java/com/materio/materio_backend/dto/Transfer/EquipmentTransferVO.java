package com.materio.materio_backend.dto.Transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentTransferVO {
    private String referenceName;
    private String serialNumber;
    private String sourceZoneName;
    private String sourceSpaceName;
    private String sourceLocalityName;
    private String targetZoneName;
    private String targetSpaceName;
    private String targetLocalityName;
    private LocalDateTime transferDate;
    private String details;
}
