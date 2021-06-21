package com.cashonline.examen.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends WithStatusException{

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
