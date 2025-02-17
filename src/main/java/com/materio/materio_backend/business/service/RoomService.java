package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Room.RoomBO;
import com.materio.materio_backend.jpa.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(RoomBO roomBO);

    void deleteRoom(String name);

    List<Room> getRoomsByLocality(String locality);
    Room getRoom(String roomName);
}
