package com.cashonline.examen.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends WithStatusException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
