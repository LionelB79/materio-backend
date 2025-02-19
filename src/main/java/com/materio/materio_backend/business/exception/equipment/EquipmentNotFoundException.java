package com.materio.materio_backend.business.exception.equipment;

public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException(String message) {
            super("L'équipement' " + message + " n'existe pas en base de donné");
    }
}
