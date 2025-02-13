package com.materio.materio_backend.dto.RoomDTO;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomBO {
    private String name;
    private String localityName;
    private List<EquipmentBO> equipments;
}
