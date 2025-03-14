package com.materio.materio_backend.business.exception.locality;

public class DuplicateLocalityException extends RuntimeException {
    public DuplicateLocalityException(String name) {
        super(String.format("Le lieu %s existe déjà", name));
    }
}
