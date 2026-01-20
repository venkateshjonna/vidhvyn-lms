package com.venkatesh.vidhvyn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venkatesh.vidhvyn.DTO.LoginRequestDTO;
import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.security.JwtUtil;
import com.venkatesh.vidhvyn.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = authService.authenticate(loginRequestDTO);
        if (user == null)
            return ResponseEntity.badRequest().body("Invalid credentials");

        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(token);
    }

}
