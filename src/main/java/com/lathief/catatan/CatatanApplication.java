package com.lathief.catatan;

import com.lathief.catatan.model.entities.note.Label;
import com.lathief.catatan.model.entities.user.Role;
import com.lathief.catatan.model.enums.ERole;
import com.lathief.catatan.repository.note.LabelRepository;
import com.lathief.catatan.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CatatanApplication implements ApplicationRunner {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	LabelRepository labelRepository;
	public static void main(String[] args) {
		SpringApplication.run(CatatanApplication.class, args);
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Role> roles = new ArrayList<>();
		roles.add(new Role(ERole.ROLE_USER));
		roles.add(new Role(ERole.ROLE_ADMIN));
		for (Role r: roles) {
			if (roleRepository.findOneRoleByName(String.valueOf(r.getName())) == null) {
				roleRepository.save(r);
			}
		}
		if (labelRepository.findOneByName("unlabeled") == null) {
			Label unlabel = new Label("unlabeled");
			labelRepository.save(unlabel);
		}
	}
}