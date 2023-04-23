package com.lathief.NoteKeep.model.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public class NotFoundException extends GeneralException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    public NotFoundException(String message){
        super(message,status);
    }
}
