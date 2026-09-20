package com.trichyflow.controller;

import com.trichyflow.model.User;
import com.trichyflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(u -> u.setPasswordHash("********")); // Mask password
        return users;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String rawPassword = payload.get("password");
        String fullName = payload.get("fullName");
        String role = payload.get("role");
        String badgeNumber = payload.get("badgeNumber");
        String email = payload.get("email");
        String phone = payload.get("phone");

        if (username == null || rawPassword == null || role == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username, password and role are required."));
        }

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists."));
        }

        String encoded = passwordEncoder.encode(rawPassword);
        User user = new User(username, encoded, fullName, role.toUpperCase(), badgeNumber, email, phone);
        User saved = userRepository.save(user);
        saved.setPasswordHash("********");
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id).map(u -> {
            userRepository.delete(u);
            return ResponseEntity.ok(Map.of("message", "User removed successfully."));
        }).orElse(ResponseEntity.notFound().build());
    }
}

