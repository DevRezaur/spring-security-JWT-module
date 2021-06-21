package dev.rezaur.jwt.service;

import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.rezaur.jwt.model.AppUser;
import dev.rezaur.jwt.model.Role;
import dev.rezaur.jwt.repository.AppUserRepo;
import dev.rezaur.jwt.repository.RoleRepo;

@Service
public class UserService {

	@Autowired
	private AppUserRepo appUserRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public AppUser findUserByUserName(String userName) {
		return appUserRepo.findByUserName(userName);
	}

	public void saveUser(AppUser appUser) {
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		Role userRole = roleRepo.findByRole("ADMIN");
		appUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	}

}
