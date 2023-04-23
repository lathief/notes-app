package com.lathief.NoteKeep.repository.note;

import com.lathief.NoteKeep.model.entities.note.Label;
import com.lathief.NoteKeep.model.entities.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    @Query(value = "UPDATE labels SET updated_at=?4, name=?2 WHERE id=?1" , nativeQuery = true)
    @Transactional
    @Modifying
    void updateNote(Long id, Date updated_at, String name);
    @Query(value = "SELECT * FROM labels WHERE name=?1", nativeQuery = true)
    Label findOneByName(String name);
    @Query(value = "SELECT label_id FROM note_label WHERE note_id=?1", nativeQuery = true)
    List<Long> getAllLabelByNoteId(Long id);
}
