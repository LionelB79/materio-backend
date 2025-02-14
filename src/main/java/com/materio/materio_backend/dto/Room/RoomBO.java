package com.materio.materio_backend.dto.Room;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomBO {
    private String name;
    private String localityName;
    private List<EquipmentBO> equipments;
}
