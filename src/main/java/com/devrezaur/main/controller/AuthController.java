package com.devrezaur.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devrezaur.main.model.User;
import com.devrezaur.main.payload.JwtResponse;
import com.devrezaur.main.security.jwt.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
		Authentication auth = null;
		
		try {
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body("Incorrect credentials!");
		}
		
		final String jwt = jwtUtils.generateToken(auth);

		return ResponseEntity.ok(new JwtResponse(jwt));
	}
}
