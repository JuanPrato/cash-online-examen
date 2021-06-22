package com.cashonline.examen.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseError {

    @JsonProperty("error")
    private final String error;

    public ResponseError(Exception ex) {
        this.error = ex.getMessage();
    }

}
