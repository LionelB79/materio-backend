package com.materio.materio_backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    ProblemDetail handleRoomNotFoundException(RoomNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
        problemDetail.setTitle("Salle non trouvée");
        return problemDetail;
    }

    @ExceptionHandler(RoomNotFoundException.class)
    ProblemDetail handleEquipmentNotFoundException(EquipmentNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
        problemDetail.setTitle("Equipment non trouvée");
        return problemDetail;
    }

    @ExceptionHandler(EquipmentLocationMismatchException.class)
    ProblemDetail handleEquipmentLocationMismatchException(EquipmentLocationMismatchException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Localisation d'équipement incorrecte");
        return problemDetail;
    }
}
