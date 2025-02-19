package com.materio.materio_backend.dto.Transfer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentTransfertDTO {
    @NotBlank(message = "Le nom de référence est obligatoire")
    private String referenceName;

    @NotBlank(message = "Le numéro de série est obligatoire")
    private String serialNumber;

    @NotBlank(message = "Le nom de la salle est obligatoire")
    private String roomName;
}
