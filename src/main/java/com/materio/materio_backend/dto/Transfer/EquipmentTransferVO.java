package com.materio.materio_backend.dto.Transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentTransferVO {
    private Set<EquipmentToTransfer> equipments = new HashSet<>();
    private String targetZoneName;
    private String targetSpaceName;
    private String targetLocalityName;
    private String details;
}
