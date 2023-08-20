package com.whathappened.whathappendbackend.service;

import com.whathappened.whathappendbackend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    // add temp userList
    ArrayList<User> userList = new ArrayList<>();
    // add password encoder from SecurityConfig
    BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userList.add(user);
        return user;
    }

    public User login(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getAllUser() {
        return userList;
    }
}
