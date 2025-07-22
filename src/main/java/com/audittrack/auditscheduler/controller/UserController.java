package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.User;
import com.audittrack.auditscheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
