package com.lathief.catatan.model.entities;

import com.lathief.catatan.model.audit.DateAudit;
import com.lathief.catatan.model.entities.note.Note;
import com.lathief.catatan.model.entities.user.User;
import com.lathief.catatan.model.enums.EPermission;
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
