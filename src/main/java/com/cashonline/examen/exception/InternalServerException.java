package com.cashonline.examen.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends WithStatusException {

    public InternalServerException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
