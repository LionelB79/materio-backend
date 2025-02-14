package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.room.RoomNotEmptyException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import com.materio.materio_backend.dto.Room.RoomBO;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.business.service.RoomService;
import com.materio.materio_backend.jpa.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void deleteRoom(RoomBO roomBO) {
       Room room = roomRepo.findByName(roomBO.getName())
               .orElseThrow(() -> new RoomNotFoundException(roomBO.getName()));

        if (!room.getEquipments().isEmpty()) {
            throw new RoomNotEmptyException(roomBO.getName());
        }

       roomRepo.delete(room);

    }

}
