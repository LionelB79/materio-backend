package com.materio.materio_backend.view.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestVO {
    private String targetRoomName;
    @JsonProperty("equipements")
    private List<EquipementVO> equipments;
    private String details;

}
