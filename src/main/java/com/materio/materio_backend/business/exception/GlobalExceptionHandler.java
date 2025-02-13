package com.materio.materio_backend.business.exception;

import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.room.RoomNotEmptyException;
import com.materio.materio_backend.business.exception.room.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
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

    @ExceptionHandler(EquipmentNotFoundException.class)
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

    @ExceptionHandler(LocalityNotFoundException.class)
    ProblemDetail handleLocalityNotFoundException(LocalityNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
        problemDetail.setTitle("Localité introuvable");
        return problemDetail;
    }

    @ExceptionHandler(DuplicateEquipmentException.class)
    ProblemDetail handleDuplicateEquipmentException(DuplicateEquipmentException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Equipment déjà existant");
        return problemDetail;
    }

    @ExceptionHandler(DuplicateLocalityException.class)
    ProblemDetail handleDuplicateLocalityException(DuplicateLocalityException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Localité déjà existante");
        return problemDetail;
    }

    @ExceptionHandler(RoomNotEmptyException.class)
    ProblemDetail handleRoomNotEmptyException(RoomNotEmptyException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Salle non vide");
        return problemDetail;
    }

}
