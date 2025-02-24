package com.materio.materio_backend.business.exception;

import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.locality.DuplicateLocalityException;
import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
import com.materio.materio_backend.business.exception.reference.InvalidQuantityException;
import com.materio.materio_backend.business.exception.space.DuplicateSpaceException;
import com.materio.materio_backend.business.exception.space.SpaceHasEquipedZonesException;
import com.materio.materio_backend.business.exception.space.SpaceNotEmptyException;
import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.business.exception.zone.DuplicateZoneException;
import com.materio.materio_backend.business.exception.zone.ZoneNotEmptyException;
import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SpaceNotFoundException.class)
    ProblemDetail handleSpaceNotFoundException(SpaceNotFoundException e) {
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

    @ExceptionHandler(SpaceNotEmptyException.class)
    ProblemDetail handleRoomNotEmptyException(SpaceNotEmptyException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Espace non vide");
        return problemDetail;
    }

    @ExceptionHandler(InvalidQuantityException.class)
    ProblemDetail handleInvalidQuantityException(InvalidQuantityException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Quantité invalide");
        return problemDetail;
    }

    @ExceptionHandler(SpaceHasEquipedZonesException.class)
    ProblemDetail handleSpaceHasEquipedZonesException(SpaceHasEquipedZonesException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Espace non vide");
        return problemDetail;
    }

    @ExceptionHandler(ZoneNotFoundException.class)
    ProblemDetail handleZoneNotFoundException(ZoneNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
        problemDetail.setTitle("Zone introuvable");
        return problemDetail;
    }

    @ExceptionHandler(DuplicateSpaceException.class)
    ProblemDetail handleDuplicateSpaceException(DuplicateSpaceException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Espace déjà existant");
        return problemDetail;
    }

    @ExceptionHandler(ZoneNotEmptyException.class)
    ProblemDetail handleZoneNotEmptyException(ZoneNotEmptyException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Zone non vide");
        return problemDetail;
    }


    @ExceptionHandler(DuplicateZoneException.class)
    ProblemDetail handleDuplicateZoneException(DuplicateZoneException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
        problemDetail.setTitle("Zone déjà existante");
        return problemDetail;
    }

}
