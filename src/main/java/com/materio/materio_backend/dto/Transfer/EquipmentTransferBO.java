package com.materio.materio_backend.dto.Transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
public class EquipmentTransferBO {
    private Set<EquipmentToTransfer> equipments = new HashSet<>();
    private String targetZoneName;
    private String targetSpaceName;
    private String targetLocalityName;
    private LocalDateTime transferDate;
    private String details;
}
