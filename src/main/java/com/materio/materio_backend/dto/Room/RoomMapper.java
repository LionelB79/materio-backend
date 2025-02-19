package com.materio.materio_backend.dto.Room;

import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.jpa.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoomMapper {

    @Autowired
    EquipmentMapper equipmentMapper;

    public Room BOtoEntity(RoomBO roomBO) {
        Room room = new Room();
        room.setName(room.getName());
        room.setLocality(room.getLocality());

        if (!room.getEquipments().isEmpty()) {
            room.setEquipments(roomBO.getEquipments().stream()
                    .map(equipment -> equipmentMapper.BOToEntity(equipment))
                    .collect(Collectors.toSet()));
        }
        return room;
    }

    public RoomVO EntityToVO(Room room) {
        RoomVO roomVO = new RoomVO();
        roomVO.setName(room.getName());
        roomVO.setLocalityName(room.getLocality().getName());

        if (!room.getEquipments().isEmpty()) {
            roomVO.setEquipments(room.getEquipments().stream()
                    .map(equipment -> equipmentMapper.EntityToVO(equipment))
                    .collect(Collectors.toSet()));
        }
        return roomVO;
    }

    public RoomBO VOToBO(RoomVO room) {
        RoomBO roomBO = new RoomBO();
        roomBO.setName(room.getName());
        roomBO.setLocalityName(room.getLocalityName());

        if (!room.getEquipments().isEmpty()) {
            roomBO.setEquipments(room.getEquipments().stream()
                    .map(equipment -> equipmentMapper.VOToBO(equipment))
                    .collect(Collectors.toSet()));
        }
        return roomBO;
    }
}
