package com.materio.materio_backend.business.BO;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomBO {
    private String name;
    private List<EquipmentBO> equipments;
}
