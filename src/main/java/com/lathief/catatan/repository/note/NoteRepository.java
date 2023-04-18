package com.lathief.catatan.repository.note;

import com.lathief.catatan.model.entities.note.Note;
import com.lathief.catatan.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(value = "UPDATE notes SET updated_at=?4, title=?2, content=?3 WHERE id=?1 " , nativeQuery = true)
    @Transactional
    @Modifying
    void updateNote(Long id, String title, String content, Date updated_at);
    @Query(value = "DELETE FROM notes WHERE id=?1", nativeQuery = true)
    void deleteOneNoteById(Long id);
    @Query("FROM Note n WHERE n.id = :id")
    Note findOneById(Long id);
}
