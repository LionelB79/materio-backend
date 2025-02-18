package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.dto.Room.RoomBO;
import com.materio.materio_backend.dto.Room.RoomMapper;
import com.materio.materio_backend.dto.Room.RoomVO;
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

    @Autowired private RoomMapper roomMapper;

    @PostMapping(value ="/room")
    public ResponseEntity<RoomVO> createRoom(@Valid @RequestBody RoomBO roomBO) {

            Room createdRoom = roomService.createRoom(roomBO);
            RoomVO createdRoomVO = roomMapper.EntityToVO(createdRoom);
            return ResponseEntity.ok(createdRoomVO);

    }

    @DeleteMapping(value = "/room/{roomName}")
    public ResponseEntity<String> deleteRoom(@RequestParam String locality, @PathVariable String roomName) {

            roomService.deleteRoom(locality, roomName);
            return ResponseEntity.ok("La salle " + roomName + " a été supprimée");
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity<List<RoomVO>> getAllRooms(@RequestParam String localityName) {
        List<Room> rooms = roomService.getRoomsByLocality(localityName);
        List<RoomVO> roomsVO = rooms.stream()
                .map(room -> roomMapper.EntityToVO(room))
                .toList();

        return ResponseEntity.ok(roomsVO);
    }

    @GetMapping(value = "/room")
    public ResponseEntity<RoomVO> getRoom(@RequestParam String localityName, @PathVariable String RoomName) {
        Room room = roomService.getRoom(localityName, RoomName);
        RoomVO roomVO = roomMapper.EntityToVO(room);

        return ResponseEntity.ok(roomVO);
    }
}
