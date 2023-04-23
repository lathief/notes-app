package com.lathief.NoteKeep.model.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ShareNoteRequest {
    private String useremail;
    private String userrole;
}
