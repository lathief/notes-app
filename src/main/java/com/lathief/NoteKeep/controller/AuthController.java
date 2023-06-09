package com.lathief.NoteKeep.controller;

import com.lathief.NoteKeep.model.entities.user.Role;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.ERole;
import com.lathief.NoteKeep.model.payload.request.SigninRequest;
import com.lathief.NoteKeep.model.payload.request.SignupRequest;
import com.lathief.NoteKeep.model.payload.response.JWTResponse;
import com.lathief.NoteKeep.service.interfaces.AuthService;
import com.lathief.NoteKeep.utils.Response;
import com.lathief.NoteKeep.utils.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@Tag(name = "User Management", description = "APIs for Managing User")
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Operation(summary = "Login User with username and password", tags = {"User Management"})
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authService.login(signinRequest));
    }
    @Operation(summary = "Register User with username, email and password", tags = {"User Management"})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.register(signupRequest));
    }
}
