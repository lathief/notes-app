package com.lathief.NoteKeep.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareNoteResponse {
    private String email;
    private String permission;
}
