package com.materio.materio_backend.business.exception;

public class EquipmentLocationMismatchException extends RuntimeException {
    public EquipmentLocationMismatchException(String equipmentName, String expectedRoom, String actualRoom) {
        super(String.format("L'équipement '%s' n'est pas dans la salle '%s' mais dans la salle '%s'",
                equipmentName, expectedRoom, actualRoom));
    }
}
