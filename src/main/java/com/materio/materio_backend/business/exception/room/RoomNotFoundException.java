package com.materio.materio_backend.business.exception.room;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String roomName) {
        super("La salle " + roomName + " n'existe pas");
    }
}

