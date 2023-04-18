package com.lathief.catatan.model.entities.note;

import com.lathief.catatan.model.audit.DateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "labels")
@NoArgsConstructor
public class Label extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Label(String name) {
        this.name = name;
    }
}
