package com.materio.materio_backend.dto.Room;

import com.materio.materio_backend.dto.Equipment.EquipmentVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomVO {
    private String name;
    private String localityName;
    private List<EquipmentVO> equipments;
}
