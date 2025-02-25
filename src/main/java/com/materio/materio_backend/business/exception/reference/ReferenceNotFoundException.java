package com.materio.materio_backend.business.exception.reference;

public class ReferenceNotFoundException extends RuntimeException {
    public ReferenceNotFoundException(String referenceName) {
        super("La référence " + referenceName + " n'existe pas");
    }
}
