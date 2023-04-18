package com.lathief.catatan.repository.note;

import com.lathief.catatan.model.entities.note.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    @Query(value = "UPDATE labels SET updated_at=?4, name=?2 WHERE id=?1" , nativeQuery = true)
    @Transactional
    @Modifying
    void updateNote(Long id, Date updated_at, String name);
}
