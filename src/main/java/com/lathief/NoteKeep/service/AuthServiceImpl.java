package com.lathief.NoteKeep.service;

import com.lathief.NoteKeep.model.entities.user.Role;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.ERole;
import com.lathief.NoteKeep.model.payload.request.SigninRequest;
import com.lathief.NoteKeep.model.payload.request.SignupRequest;
import com.lathief.NoteKeep.model.payload.response.JWTResponse;
import com.lathief.NoteKeep.repository.user.RoleRepository;
import com.lathief.NoteKeep.repository.user.UserRepository;
import com.lathief.NoteKeep.service.interfaces.AuthService;
import com.lathief.NoteKeep.utils.Response;
import com.lathief.NoteKeep.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Response response;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;
    public Map login(SigninRequest signinRequest) {
        User getUser = userRepository.findOneByUsername(signinRequest.getUsername());
        if (getUser == null){
            return response.notFound("User not found");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateJwtAccessToken(authentication);

        return response.success(new JWTResponse(accessToken));
    }

    public Map register(SignupRequest signupRequest) {
        if (userRepository.checkExistingUsername(signupRequest.getUsername()) != null){
            return response.badRequest("Error: Username is already in use!");
        }
        if (userRepository.checkExistingEmail(signupRequest.getEmail()) != null) {
            return response.badRequest("Error: Email is already in use!");
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findOneRoleByName(String.valueOf(ERole.ROLE_USER));
            if (userRole == null) {
                throw new RuntimeException("Error: Role " + ERole.ROLE_USER + " is not found");
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole = roleRepository.findOneRoleByName(role);
                if (userRole == null) {
                    throw new RuntimeException("Error: Role " + role + " is not found");
                }
                roles.add(userRole);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return response.created("User registration successfully");
    }
}
