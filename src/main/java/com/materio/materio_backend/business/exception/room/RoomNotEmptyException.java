package com.materio.materio_backend.business.exception.room;

public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String roomName) {
        super("Transférez ou supprimez l'équipement présent dans la salle " + roomName +" avant de la supprimer");
    }
}
