package com.cashonline.examen.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class WithStatusException extends Exception{

    private final HttpStatus status;

    public WithStatusException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
