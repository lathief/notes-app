package com.lathief.NoteKeep.model.exception;

import org.springframework.http.HttpStatus;

public class GeneralException extends RuntimeException{
    private HttpStatus httpStatus;
    public GeneralException(String message, HttpStatus httpStatus) {

        super(message);
        this.httpStatus = httpStatus;
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}
