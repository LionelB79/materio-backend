package com.materio.materio_backend.dto.Transfer;

import lombok.Data;

@Data
public class EquipmentToTransfer {
    private String referenceName;
    private String serialNumber;
    private String sourceZoneName;
    private String sourceSpaceName;
}
