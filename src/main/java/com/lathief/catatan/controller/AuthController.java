package com.lathief.catatan.controller;

import com.lathief.catatan.model.entities.user.Role;
import com.lathief.catatan.model.entities.user.User;
import com.lathief.catatan.model.enums.ERole;
import com.lathief.catatan.model.payload.request.SigninRequest;
import com.lathief.catatan.model.payload.request.SignupRequest;
import com.lathief.catatan.model.payload.response.JWTResponse;
import com.lathief.catatan.service.interfaces.AuthService;
import com.lathief.catatan.utils.Response;
import com.lathief.catatan.utils.jwt.JwtUtils;
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
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    Response response;
    @Autowired
    PasswordEncoder encoder;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        User getUser = authService.getUserByUsername(signinRequest.getUsername());
        if (getUser == null){
            return ResponseEntity.notFound().build();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateJwtAccessToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new JWTResponse(accessToken));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (authService.checkExistUserByUsername(signupRequest.getUsername()) != null){
            return ResponseEntity
                    .badRequest()
                    .body(response.badRequest("Error: Username is already taken!"));
        }
        if (authService.checkExistUserByEmail(signupRequest.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(response.badRequest("Error: Email is already in use!"));
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = authService.getRole(String.valueOf(ERole.ROLE_USER));
            if (userRole == null) {
                throw new RuntimeException("Error: Role " + ERole.ROLE_USER + " is not found");
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole = authService.getRole(role);
                if (userRole == null) {
                    throw new RuntimeException("Error: Role " + role + " is not found");
                }
                roles.add(userRole);
            });
        }

        user.setRoles(roles);
        return ResponseEntity.ok(authService.register(user));
    }
}
