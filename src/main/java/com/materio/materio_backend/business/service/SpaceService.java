package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Room.RoomBO;
import com.materio.materio_backend.jpa.entity.Space;

import java.util.List;

public interface SpaceService {

    void createRoom(RoomBO roomBO);

    void deleteRoom(String locality, String name);

    List<Space> getSpacesByLocality(String locality);
    Space getSpace(String locality, String spaceName);
}
