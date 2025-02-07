package com.materio.materio_backend.service;

import com.materio.materio_backend.model.BO.RoomBO;
import com.materio.materio_backend.model.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(RoomBO roomBO);

    List<Room> getAllRooms();
    Room getRoomByName(String name);
    void deleteRoom(String name);
}
