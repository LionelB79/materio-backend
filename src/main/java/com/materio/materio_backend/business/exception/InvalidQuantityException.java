package com.materio.materio_backend.business.exception;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException(String referenceName) {
        super("La quantité ne peut pas être négative pour la référence : " + referenceName);
    }
}
