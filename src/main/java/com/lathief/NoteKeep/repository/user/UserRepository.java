package com.lathief.NoteKeep.repository.user;

import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    User findOneByEmail(String email);
    @Query("FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    User findOneByUsername(String username);
    @Query("FROM User u WHERE  LOWER(u.username) = LOWER(:username)")
    User checkExistingUsername(String username);
    @Query("FROM User u WHERE LOWER(u.email) = LOWER(:username)")
    User checkExistingEmail(String username);
    @Query("FROM User u WHERE u.id = :id")
    User findOneById(Long id);
}
