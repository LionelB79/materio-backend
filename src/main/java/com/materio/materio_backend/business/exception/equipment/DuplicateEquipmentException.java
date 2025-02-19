package com.materio.materio_backend.business.exception.equipment;

public class DuplicateEquipmentException extends RuntimeException {
    public DuplicateEquipmentException(String referenceName, String serialNumber) {
        super(String.format("L'équipement %s avec le numéro de série %s existe déjà", referenceName, serialNumber));
    }
}
