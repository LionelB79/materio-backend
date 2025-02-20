package com.materio.materio_backend.business.exception;

public class EquipmentLocationMismatchException extends RuntimeException {
    public EquipmentLocationMismatchException(
            String equipmentName,
            String expectedZone,
            String expectedSpace,
            String expectedLocality,
            String actualZone,
            String actualSpace,
            String actualLocality) {
        super(String.format(
                "L'équipement '%s' n'est pas dans la zone '%s' de l'espace '%s' de la localité '%s' " +
                        "mais dans la zone '%s' de l'espace '%s' de la localité '%s'",
                equipmentName,
                expectedZone, expectedSpace, expectedLocality,
                actualZone, actualSpace, actualLocality));
    }
}
