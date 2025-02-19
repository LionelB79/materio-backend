package com.materio.materio_backend.business.exception.locality;

public class LocalityNotEmptyException extends RuntimeException {
    public LocalityNotEmptyException(String name) {
        super(String.format("Impossible de supprimer le lieu %s car il contient des salles avec des équipements", name));
    }
}
