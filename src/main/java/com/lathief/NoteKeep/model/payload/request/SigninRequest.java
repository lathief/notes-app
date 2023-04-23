package com.lathief.NoteKeep.model.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class SigninRequest {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
}
