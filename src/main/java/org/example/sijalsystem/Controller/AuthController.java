package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.DTO.IN.LoginRequestDTO;
import org.example.sijalsystem.DTO.IN.LoginResponse;
import org.example.sijalsystem.Security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/auth")
    @RequiredArgsConstructor
    public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil;

        @PostMapping("/login")
        public ResponseEntity<?> login(
                @RequestBody LoginRequestDTO request) {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );

            String token = jwtUtil.generateToken(
                    (UserDetails) authentication.getPrincipal()
            );

            return ResponseEntity.ok(token);

        }
    }

