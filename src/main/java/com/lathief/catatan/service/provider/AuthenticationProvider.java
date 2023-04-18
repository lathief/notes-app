package com.lathief.catatan.service.provider;

import org.springframework.security.core.Authentication;

public interface AuthenticationProvider {
    Authentication getAuthentication();
}
