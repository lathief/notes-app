package com.lathief.NoteKeep.service.interfaces;

import com.lathief.NoteKeep.model.entities.user.Role;
import com.lathief.NoteKeep.model.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {
    Map login();
    Map register(User user);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User checkExistUserByEmail(String email);
    User checkExistUserByUsername(String username);
    Role getRole(String name);
}
