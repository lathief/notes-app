package com.lathief.NoteKeep.repository.user;

import com.lathief.NoteKeep.model.entities.user.Role;
import com.lathief.NoteKeep.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("FROM Role r WHERE LOWER(r.name) = LOWER(:name)")
    Role findOneRoleByName(String name);
}
