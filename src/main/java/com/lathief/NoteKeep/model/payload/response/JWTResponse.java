package com.lathief.NoteKeep.model.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class JWTResponse {
    private String type = "Bearer";
    private String accessToken;

    public JWTResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
