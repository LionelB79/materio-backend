package com.materio.materio_backend.business.exception.space;

public class SpaceNotFoundException extends RuntimeException {
    public SpaceNotFoundException(String roomName) {
        super("La salle " + roomName + " n'existe pas");
    }
}

