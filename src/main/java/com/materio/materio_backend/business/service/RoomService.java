package com.materio.materio_backend.business.service;

import com.materio.materio_backend.business.BO.RoomBO;
import com.materio.materio_backend.jpa.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(RoomBO roomBO);

    List<Room> getAllRooms();
    Room getRoomByName(String name);
    void deleteRoom(String name);
}
