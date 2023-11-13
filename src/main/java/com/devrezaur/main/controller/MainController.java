package com.devrezaur.main.controller;

import com.devrezaur.main.model.JwtResponse;
import com.devrezaur.main.model.User;
import com.devrezaur.main.service.UserService;
import com.devrezaur.main.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller class of the application.
 *
 * @author Rezaur Rahman
 */
@RestController
public class MainController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    /**
     * Constructor for MainController class.
     *
     * @param authenticationManager the instance of authentication manager.
     * @param jwtUtils              the instance of utility class for JWT operations.
     * @param userService           the instance of user service class.
     */
    public MainController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    /**
     * Handles the authentication request and generates a JWT token.
     *
     * @param user the user credentials.
     * @return ResponseEntity containing the JWT token.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect credentials!", HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder()
                .type("Bearer")
                .username(user.getUsername())
                .token(jwt)
                .build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    /**
     * Registers a new user.
     *
     * @param user the user to be registered.
     * @return ResponseEntity indicating the registration status.
     */
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User existingUser = userService.findUserByUsername(user.getUsername());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Registers a new admin.
     * <p>
     * To use this API, client application needs to pass access token with role 'ADMIN'.
     *
     * @param user the admin to be registered.
     * @return ResponseEntity indicating the registration status.
     */
    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        User existingUser = userService.findUserByUsername(user.getUsername());

        if (existingUser != null)
            return ResponseEntity.badRequest().body("User already exists!");

        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Dummy controller endpoint for regular users.
     * <p>
     * To use this API, client application needs to pass access token with role 'USER' or 'ADMIN'.
     *
     * @return Welcome message.
     */
    @GetMapping("/user")
    public String welcomeUser() {
        return "Welcome to user controller";
    }

    /**
     * Dummy controller endpoint for admin users.
     * <p>
     * To use this API, client application needs to pass access token with role 'ADMIN'.
     *
     * @return Welcome message.
     */
    @GetMapping("/admin")
    public String welcomeAdmin() {
        return "Welcome to admin controller";
    }

}
