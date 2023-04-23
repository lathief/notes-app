package com.lathief.NoteKeep.service.provider;

import org.springframework.security.core.Authentication;

public interface AuthenticationProvider {
    Authentication getAuthentication();
}
