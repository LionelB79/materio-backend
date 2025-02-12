package com.materio.materio_backend.view.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipementVO {
    private Long id;
    private String referenceName;
    private String mark;
    private String description;
    private String roomName;
    private int quantity;

}
