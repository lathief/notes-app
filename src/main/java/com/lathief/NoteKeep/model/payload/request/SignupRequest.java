package com.lathief.NoteKeep.model.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    @NotEmpty(message = "Username is required.")
    @Size(min = 3, max = 20)
    private String username;
    @NotEmpty(message = "Email is required.")
    @Size(max = 50)
    @Email(message = "please insert valid email")
    private String email;
    @NotEmpty(message = "password is required.")
    @Size(min = 6, message = "input is too short, please make sure at least 6 characters")
    private String password;
    private Set<String> role;
}
