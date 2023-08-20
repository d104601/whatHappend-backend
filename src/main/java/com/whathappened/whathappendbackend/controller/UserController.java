package com.whathappened.whathappendbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.whathappened.whathappendbackend.domain.User;
import com.whathappened.whathappendbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    Logger logger;

    public UserController(UserService userService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @GetMapping("/test")
    public List<User> test() {
        logger.info("test");
        return userService.getAllUser();
    }

    @PostMapping("/register")
    public User register(
            @RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody String username, @RequestBody String password) {
        logger.info("login: username: " + username + ", password: " + password);
        return userService.login(username, password);
    }
}
