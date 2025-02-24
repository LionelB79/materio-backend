package com.materio.materio_backend.business.exception.zone;

public class DuplicateZoneException extends RuntimeException {
    public DuplicateZoneException(String zoneName) {
      super(String.format("Le lieu %s existe déjà", zoneName));
    }
}
