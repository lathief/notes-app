package com.lathief.NoteKeep.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class DateAudit {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true, updatable = false)
    @CreatedDate
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    @UpdateTimestamp
    private Date updatedAt;
}
