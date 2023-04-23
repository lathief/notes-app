package com.lathief.NoteKeep.model.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@NoArgsConstructor
public class ShareNoteRequest {
    @NotEmpty(message = "Email is required.")
    @Email(message = "please insert valid email")
    private String email;
    @NotEmpty(message = "Permission is required.")
    private String permission;
}
