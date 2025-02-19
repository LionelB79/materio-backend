package com.materio.materio_backend.business.exception;

public class EquipmentLocationMismatchException extends RuntimeException {
    public EquipmentLocationMismatchException(
            String equipmentName,
            String expectedRoom,
            String expectedLocality,
            String actualRoom,
            String actualLocality) {
        super(String.format("L'équipement '%s' n'est pas dans la salle '%s' de la localité '%s' mais dans la salle '%s' de la localité '%s'",
                equipmentName, expectedRoom, expectedLocality, actualRoom, actualLocality));
    }
}
