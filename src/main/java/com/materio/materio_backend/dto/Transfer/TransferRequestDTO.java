package com.materio.materio_backend.dto.Transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    @NotBlank(message = "La locality cible est obligatoire")
    private String targetLocality;

    @NotBlank(message = "La salle cible est obligatoire")
    private String targetRoomName;

    @JsonProperty("equipements")
    @NotEmpty(message = "La liste des équipements ne peut pas être vide")
    private List<EquipmentTransfertDTO> equipments;

    private String details;

}
