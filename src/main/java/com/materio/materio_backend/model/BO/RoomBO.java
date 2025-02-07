package com.materio.materio_backend.model.BO;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBO {
    private String name;
    private List<EquipmentBO> equipments;
}
