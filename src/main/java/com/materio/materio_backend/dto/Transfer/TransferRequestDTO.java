package com.materio.materio_backend.dto.Transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    private String targetRoomName;
    @JsonProperty("equipements")
    private List<EquipmentTransfertDTO> equipments;
    private String details;

}
