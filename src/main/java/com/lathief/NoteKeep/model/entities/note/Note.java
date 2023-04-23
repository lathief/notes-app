package com.lathief.NoteKeep.model.entities.note;

import com.lathief.NoteKeep.model.audit.DateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "notes")
public class Note extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToMany(targetEntity = Label.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "note_label",
            joinColumns = {
                    @JoinColumn(name = "note_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "label_id")
            }
    )
    private Set<Label> labels = new HashSet<>();
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
