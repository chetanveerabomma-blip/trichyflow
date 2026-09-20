package com.trichyflow.controller;

import com.trichyflow.model.User;
import com.trichyflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private final org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        String role = credentials.get("role");

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (role != null && !role.equalsIgnoreCase(user.getRole())) {
                return ResponseEntity.status(401).body(Map.of("message", "Role mismatch for user."));
            }

            boolean matches = passwordEncoder.matches(password, user.getPasswordHash()) ||
                              password.equals(user.getPasswordHash());
            if (!matches) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password."));
            }

            Map<String, Object> resp = new HashMap<>();
            resp.put("id", user.getId());
            resp.put("username", user.getUsername());
            resp.put("fullName", user.getFullName());
            resp.put("role", user.getRole());
            resp.put("badgeNumber", user.getBadgeNumber());
            resp.put("token", "jwt-trichyflow-" + System.currentTimeMillis());
            return ResponseEntity.ok(resp);
        }

        // Default demo fallback verification
        if (("admin".equals(username) && "admin123".equals(password)) ||
            ("police".equals(username) && "police123".equals(password)) ||
            ("control".equals(username) && "control123".equals(password))) {
            
            Map<String, Object> resp = new HashMap<>();
            resp.put("username", username);
            resp.put("fullName", "admin".equals(username) ? "Commissioner S. Ramanathan" : ("police".equals(username) ? "Inspector M. Anbarasan" : "Operator K. Selvam"));
            resp.put("role", role != null ? role : "CONTROL_ROOM");
            resp.put("badgeNumber", "admin".equals(username) ? "IPS-TR-01" : ("police".equals(username) ? "TP-CHATH-42" : "CR-SYS-108"));
            resp.put("token", "jwt-trichyflow-demo-" + System.currentTimeMillis());
            return ResponseEntity.ok(resp);
        }

        return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password."));
    }
}

