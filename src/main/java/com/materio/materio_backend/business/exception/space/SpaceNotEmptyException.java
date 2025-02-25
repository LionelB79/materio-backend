package com.materio.materio_backend.business.exception.space;

public class SpaceNotEmptyException extends RuntimeException {
    public SpaceNotEmptyException(String roomName) {
        super("Transférez ou supprimez l'équipement présent dans la salle : " + roomName +" avant de la supprimer");
    }
}
