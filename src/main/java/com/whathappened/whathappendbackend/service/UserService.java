package com.whathappened.whathappendbackend.service;

import com.whathappened.whathappendbackend.domain.User;
import com.whathappened.whathappendbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    BCryptPasswordEncoder passwordEncoder;
    UserRepository userRepository;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user;
        // check if it's username or email
        if(username.contains("@")) {
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByUsername(username);
        }
        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user;
            }
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
