package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Room.RoomBO;
import com.materio.materio_backend.jpa.entity.Room;

public interface RoomService {

    Room createRoom(RoomBO roomBO);

    void deleteRoom(RoomBO roomBO);


}
