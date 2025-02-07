package com.materio.materio_backend.model.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBO {
    private String name;
    private List<EquipmentBO> equipments;
}
