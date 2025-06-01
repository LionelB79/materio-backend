package com.materio.materio_backend.dto.Transfer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
public class EquipmentTransferBO {
    private Long id;

    private Set<Long> equipmentIds = new HashSet<>();

    @NotNull(message = "L'ID de la zone cible est obligatoire")
    private Long targetZoneId;

    private LocalDateTime transferDate;
    private String details;
}
