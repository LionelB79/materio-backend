package com.materio.materio_backend.business.exception;

public class ReferenceNotFoundException extends RuntimeException {
    public ReferenceNotFoundException(String referenceName) {
        super("La référence " + referenceName + " n'existe pas");
    }
}
