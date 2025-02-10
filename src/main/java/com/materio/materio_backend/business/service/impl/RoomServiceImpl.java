package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.BO.RoomBO;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.business.service.RoomService;
import com.materio.materio_backend.jpa.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepo;

    @Override

    public Room createRoom(RoomBO roomBO) {
        roomRepo.findByName(roomBO.getName())
                .ifPresent(r -> {
                    throw new RuntimeException("La salle est déjà existante"); });

        Room room = new Room();
        room.setName(roomBO.getName());
        return roomRepo.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return List.of();
    }

    @Override
    public Room getRoomByName(String name) {
        return null;
    }

    @Override
    public void deleteRoom(String name) {

    }
}
