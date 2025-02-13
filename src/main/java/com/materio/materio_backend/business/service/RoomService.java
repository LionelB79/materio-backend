package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.RoomDTO.RoomBO;
import com.materio.materio_backend.jpa.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(RoomBO roomBO);

    void deleteRoom(RoomBO roomBO);

    List<Room> getAllRooms(String locality);
}
