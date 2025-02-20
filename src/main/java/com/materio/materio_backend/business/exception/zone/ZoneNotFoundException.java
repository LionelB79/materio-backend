package com.materio.materio_backend.business.exception.zone;

public class ZoneNotFoundException extends RuntimeException {
    public ZoneNotFoundException(String zoneName) {
        super("La zone " + zoneName + " n'existe pas");
    }
}
