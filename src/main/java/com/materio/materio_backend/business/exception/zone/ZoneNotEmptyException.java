package com.materio.materio_backend.business.exception.zone;

public class ZoneNotEmptyException extends RuntimeException {
    public ZoneNotEmptyException(String zoneName) {
        super("Transférez ou supprimez l'équipement présent dans la zone : " + zoneName +" avant de la supprimer");
    }
}
