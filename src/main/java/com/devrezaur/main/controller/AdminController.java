package com.devrezaur.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devrezaur.main.model.User;
import com.devrezaur.main.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String welcome() {
		return "Welcome to admin controller";
	}
	
	@PostMapping("/registerAdmin")
	public ResponseEntity<?> registerAdmin(@RequestBody User user) {
		User regUser = userService.findUserByUsername(user.getUsername());
		
		if(regUser != null)
			return ResponseEntity.badRequest().body("User already exists!");
		
		regUser = userService.saveAdmin(user);
		
		return ResponseEntity.ok().body(regUser);
	}

}
