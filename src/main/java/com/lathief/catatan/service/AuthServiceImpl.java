package com.lathief.catatan.service;

import com.lathief.catatan.model.entities.user.Role;
import com.lathief.catatan.model.entities.user.User;
import com.lathief.catatan.repository.user.RoleRepository;
import com.lathief.catatan.repository.user.UserRepository;
import com.lathief.catatan.service.interfaces.AuthService;
import com.lathief.catatan.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Response response;
    public Map login() {
        return null;
    }

    public Map register(User user) {
        userRepository.save(user);
        return response.created("User registration successfully");
    }

    public User getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public User checkExistUserByEmail(String email) {
        return userRepository.checkExistingEmail(email);
    }

    public User checkExistUserByUsername(String username) {
        return userRepository.checkExistingUsername(username);
    }

    public Role getRole(String name) {
        return roleRepository.findOneRoleByName(name);
    }
}
