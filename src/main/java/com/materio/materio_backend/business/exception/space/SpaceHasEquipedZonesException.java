package com.materio.materio_backend.business.exception.space;

public class SpaceHasEquipedZonesException extends RuntimeException {
    public SpaceHasEquipedZonesException(String spaceName) {
        super("L'espace"  + spaceName +  "contient des équipements dans ses zones");
    }
}
