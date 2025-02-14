package com.materio.materio_backend.dto.Room;

import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.jpa.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    @Autowired
    private EquipmentMapper equipmentMapper;

    public Room BOToEntity(RoomBO roomBO) {
        Room room = new Room();
        room.setName(roomBO.getName());
        return room;
    }

    public RoomVO entityToVO(Room room) {
        RoomVO roomVO = new RoomVO();
        roomVO.setName(room.getName());
        roomVO.setLocalityName(room.getLocality().getName());


        // Conversion des équipements
        if (null !=room.getEquipments()) {
            roomVO.setEquipments(room.getEquipments().stream()
                    .map(equipment -> equipmentMapper.EntityToVO(equipment))
                    .toList());
        }

        return roomVO;
    }
}