package com.lathief.NoteKeep.model.entities;

import com.lathief.NoteKeep.model.audit.DateAudit;
import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.EPermission;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_note")
public class UserNote implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EPermission permission;

    public UserNote(Note note, User user, EPermission permission) {
        this.note = note;
        this.user = user;
        this.permission = permission;
    }
}
