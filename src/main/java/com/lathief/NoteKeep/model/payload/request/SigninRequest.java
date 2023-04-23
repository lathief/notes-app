package com.lathief.NoteKeep.model.payload.request;

import javax.validation.constraints.*;

import lombok.Data;


@Data
public class SigninRequest {
    @NotEmpty(message = "Username is required.")
    private String username;
    @NotEmpty(message = "password is required.")
    @Size(min = 6, message = "input is too short, please make sure at least 6 characters")
    private String password;
}
