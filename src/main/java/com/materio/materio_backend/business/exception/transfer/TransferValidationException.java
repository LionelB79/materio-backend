package com.materio.materio_backend.business.exception.transfer;

public class TransferValidationException extends RuntimeException {
    public TransferValidationException(String message) {
        super(message);
    }
}
