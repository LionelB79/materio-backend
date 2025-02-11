package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.BO.RoomBO;
import com.materio.materio_backend.jpa.entity.Room;
import com.materio.materio_backend.business.service.RoomService;
import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @PostMapping(value ="/room", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> createRoom(@RequestBody RoomBO roomBO) {
        try {
            Room createdRoom = roomService.createRoom(roomBO);
            return ResponseEntity.ok(createdRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


