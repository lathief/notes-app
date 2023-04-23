package com.lathief.NoteKeep.model.payload.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MessageResponse {
    private Object obj;
    private HttpStatus httpStatus;

    public MessageResponse(Object obj, HttpStatus httpStatus) {
        this.obj = obj;
        this.httpStatus = httpStatus;
    }
}
