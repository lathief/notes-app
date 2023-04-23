package com.lathief.NoteKeep.service;

import com.lathief.NoteKeep.model.entities.note.Label;
import com.lathief.NoteKeep.model.entities.user.Role;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.ERole;
import com.lathief.NoteKeep.repository.note.LabelRepository;
import com.lathief.NoteKeep.repository.user.RoleRepository;
import com.lathief.NoteKeep.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseSeeder implements ApplicationRunner {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        roles.add(new Role(ERole.ROLE_ADMIN));
        for (Role r: roles) {
            if (roleRepository.findOneRoleByName(String.valueOf(r.getName())) == null) {
                roleRepository.save(r);
            }
        }
        if (userRepository.findOneByUsername("admin") == null) {
            Set<Role> rolesAdmin = new HashSet<Role>();
            Role admin = roleRepository.findOneRoleByName(String.valueOf(ERole.ROLE_ADMIN));
            rolesAdmin.add(admin);
            User adminUser = new User("admin", "admin@admin.com", encoder.encode("admin1234"));
            adminUser.setRoles(rolesAdmin);
            userRepository.save(adminUser);
        }
    }
}
