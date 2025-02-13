package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.RoomDTO.RoomBO;
import com.materio.materio_backend.jpa.entity.Room;

public interface RoomService {

    Room createRoom(RoomBO roomBO);


}
