package com.materio.materio_backend.dto.Transfer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipmentToTransfer {

    @NotNull(message = "L'ID de l'équipement est obligatoire")
    private Long equipmentId;

    @NotNull(message = "L'ID de la zone source est obligatoire")
    private Long sourceZoneId;
    private String referenceName;
    private String serialNumber;
    private String sourceZoneName;
    private String sourceSpaceName;
}
