package com.lathief.NoteKeep.service.interfaces;

import com.lathief.NoteKeep.model.entities.user.Role;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.payload.request.SigninRequest;
import com.lathief.NoteKeep.model.payload.request.SignupRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {
    Map login(SigninRequest signinRequest);
    Map register(SignupRequest signupRequest);
}
