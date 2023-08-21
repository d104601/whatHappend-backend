package com.whathappened.whathappendbackend.controller;

import com.whathappened.whathappendbackend.domain.LoginResponse;
import com.whathappened.whathappendbackend.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.whathappened.whathappendbackend.domain.User;
import com.whathappened.whathappendbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    Logger logger;
    JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(UserController.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/test")
    public List<User> test() {
        logger.info("test");
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public User register(
            @RequestBody User user) {
        logger.info("method: register called");
        logger.info("received data: username: " + user.getUsername() + ", password: " + user.getPassword());
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody HashMap<String, String> credentials) {
        logger.info("method: login called");
        String username = credentials.get("username");
        String password = credentials.get("password");
        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {
            String token = jwtTokenProvider.createToken(username);
            logger.info("user found, token created");
            return LoginResponse.builder()
                    .token(token)
                    .code(200)
                    .build();
        } else {
            logger.info("user not found");
            return null;
        }
    }
}
