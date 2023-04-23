package com.lathief.NoteKeep.repository;

import com.lathief.NoteKeep.model.entities.UserNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NoteUserRepository extends JpaRepository<UserNote, Long> {
    @Query(value = "SELECT * FROM user_note WHERE note_id = ?1", nativeQuery = true)
    List<UserNote> findOneNoteById(Long noteid);
    @Query(value = "SELECT * FROM user_note WHERE user_id = ?1", nativeQuery = true)
    List<UserNote> findOneUserById(Long userid);
    @Query(value = "SELECT permission FROM user_note WHERE user_id = ?1 and note_id = ?2", nativeQuery = true)
    String findPermission(Long userid, Long noteid);
    @Query(value = "DELETE FROM user_note WHERE note_id = ?1", nativeQuery = true)
    @Modifying
    void deleteOneNoteById(Long id);
}
