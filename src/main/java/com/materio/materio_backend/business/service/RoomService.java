package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Room.RoomBO;
import com.materio.materio_backend.dto.Room.RoomVO;
import com.materio.materio_backend.jpa.entity.Room;

import java.util.List;

public interface RoomService {

    void createRoom(RoomBO roomBO);

    void deleteRoom(String locality, String name);

    List<Room> getRoomsByLocality(String locality);
    Room getRoom(String locality, String roomName);
}
