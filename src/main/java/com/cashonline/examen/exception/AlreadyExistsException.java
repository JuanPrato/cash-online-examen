package com.cashonline.examen.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends WithStatusException{

    public AlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

}
