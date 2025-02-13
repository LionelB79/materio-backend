package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.dto.RoomDTO.RoomBO;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.business.service.RoomService;
import jakarta.validation.Valid;
import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @PostMapping(value ="/room")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody RoomBO roomBO) {
        try {
            Room createdRoom = roomService.createRoom(roomBO);
            return ResponseEntity.ok(createdRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/room")
    public ResponseEntity<String> deleteRoom(@Valid @RequestBody RoomBO roomBO) {

            roomService.deleteRoom(roomBO);
            return ResponseEntity.ok("La salle " + roomBO + " a été supprimée");
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity<List<Room>> getAllRooms(@Valid @RequestBody LocalityBO localityBO) {
        List<Room> rooms = roomService.getAllRooms(localityBO.getName());
        return ResponseEntity.ok(rooms);
    }
}
