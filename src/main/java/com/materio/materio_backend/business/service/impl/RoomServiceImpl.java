package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.room.RoomNotEmptyException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import com.materio.materio_backend.dto.Room.RoomBO;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.business.service.RoomService;
import com.materio.materio_backend.jpa.repository.LocalityRepository;
import com.materio.materio_backend.jpa.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepo;
    @Autowired
    LocalityServiceImpl localityService;


    @Override
    public Room createRoom(RoomBO roomBO) {
        roomRepo.findByName(roomBO.getName())
                .ifPresent(r -> {
                    throw new RuntimeException("La salle est déjà existante"); });

        Locality locality = localityService.getLocalityByName(roomBO.getLocalityName());

        Room room = new Room();
        room.setName(roomBO.getName());
        room.setLocality(locality);
        return roomRepo.save(room);
    }

    public void deleteRoom(String roomName) {
       Room room = roomRepo.findByName(roomName)
               .orElseThrow(() -> new RoomNotFoundException(roomName));

        if (!room.getEquipments().isEmpty()) {
            throw new RoomNotEmptyException(roomName);
        }

       roomRepo.delete(room);

    }

    @Override
    public List<Room> getRooms(String localityName) {

        final Locality locality = localityService.getLocalityByName(localityName);

        return roomRepo.findByLocality_Name(localityName);
    }
}
