package com.materio.materio_backend.dto.Transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentTransfertDTO {
    private String referenceName;
    private String serialNumber;
    private String roomName;
}
