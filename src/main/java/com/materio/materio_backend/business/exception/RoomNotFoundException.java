package com.materio.materio_backend.business.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String roomName) {
        super("La salle " + roomName + " n'existe pas");
    }
}

