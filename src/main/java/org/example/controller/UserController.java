package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entity.User;
import org.example.service.AuthService;
import org.example.util.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = authService.register(user);
            String token = jwtUtil.generateToken(registeredUser);
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User loggedInUser = authService.login(username, password);
        if (loggedInUser != null) {
            String token = jwtUtil.generateToken(loggedInUser);
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
