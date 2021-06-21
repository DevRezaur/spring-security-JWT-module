package dev.rezaur.jwt.controller;

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
import dev.rezaur.jwt.model.AppUser;
import dev.rezaur.jwt.model.JwtResponse;
import dev.rezaur.jwt.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AppUser appUser) throws Exception {
		Authentication auth = null;
		try {
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final String jwt = jwtUtil.generateToken(auth);

		return ResponseEntity.ok(new JwtResponse(jwt));
	}

}
