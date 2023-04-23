package com.lathief.NoteKeep.model.entities;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class UserNoteKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "note_id")
    private Long noteId;

    public UserNoteKey(Long userId, Long noteId) {
        this.userId = userId;
        this.noteId = noteId;
    }
}
