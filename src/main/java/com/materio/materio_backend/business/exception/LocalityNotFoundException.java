package com.materio.materio_backend.business.exception;

public class LocalityNotFoundException extends RuntimeException {
    public LocalityNotFoundException(String message) {
        super("La localité " + message + " n'existe pas");
    }
}
