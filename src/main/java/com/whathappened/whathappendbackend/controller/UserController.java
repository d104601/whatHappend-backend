package com.whathappened.whathappendbackend.controller;

import com.whathappened.whathappendbackend.domain.LoginResponse;
import com.whathappened.whathappendbackend.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.whathappened.whathappendbackend.domain.User;
import com.whathappened.whathappendbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    Logger logger;
    JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(UserController.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/test")
    public List<User> test() {
        logger.info("test");
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (
            @RequestBody User user) throws RuntimeException {
        logger.info("method: register called");
        logger.info("received data: username: " + user.getUsername() + ", password: " + user.getPassword());

        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        }
        catch (RuntimeException e) {
            logger.error("Error: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HashMap<String, String> credentials) throws AuthenticationException{
        logger.info("method: login called");

        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // if authentication is successful, create a jwt token and return it
            // if bad credentials, return 401
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String jwt = jwtTokenProvider.createToken(userDetails.getUsername());
            return ResponseEntity.ok(new LoginResponse(jwt, "login successful"));
        }
        // send 401 with message
        catch (AuthenticationException e) {
            logger.error("Error: ", e);
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}
