package com.materio.materio_backend.business.exception;

public class SpaceHasEquipedZonesException extends RuntimeException {
    public SpaceHasEquipedZonesException(String spaceName) {
        super("L'espace"  + spaceName +  "contient des équipements dans ses zones");
    }
}
