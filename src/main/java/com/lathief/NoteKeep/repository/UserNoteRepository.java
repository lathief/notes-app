package com.lathief.NoteKeep.repository;

import com.lathief.NoteKeep.model.entities.UserNote;
import com.lathief.NoteKeep.model.entities.UserNoteKey;
import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserNoteRepository extends JpaRepository<UserNote, Long> {
    @Query(value = "SELECT * FROM user_note WHERE note_id = ?1", nativeQuery = true)
    List<UserNote> findOneNoteById(Long noteid);
    @Query(value = "SELECT * FROM user_note WHERE user_id = ?1", nativeQuery = true)
    List<UserNote> findOneUserById(Long userid);
    @Query(value = "DELETE FROM user_note WHERE note_id = ?1", nativeQuery = true)
    @Modifying
    void deleteOneNoteById(Long id);
}
