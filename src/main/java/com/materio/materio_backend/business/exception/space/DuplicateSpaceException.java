package com.materio.materio_backend.business.exception.space;

public class DuplicateSpaceException extends RuntimeException {
    public DuplicateSpaceException(String zoneName) {
        super(String.format("La zone %s existe déjà", zoneName));
    }
}
