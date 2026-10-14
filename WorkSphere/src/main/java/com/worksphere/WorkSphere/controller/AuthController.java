package com.worksphere.WorkSphere.controller;

import com.worksphere.WorkSphere.dto.*;
import com.worksphere.WorkSphere.entity.User;
import com.worksphere.WorkSphere.enums.Role;
import com.worksphere.WorkSphere.repository.UserRepository;
import com.worksphere.WorkSphere.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // ================= REGISTER =================

    @PostMapping("/register")
    public LoginResponseDTO register(
            @RequestBody RegisterRequestDTO request) {

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())

                // IMPORTANT
                .password(
                        passwordEncoder.encode(request.getPassword()))

                .roles(new HashSet<>())
                .build();

        // Default role
        user.getRoles().add(Role.EMPLOYEE);

        System.out.println("User in auth controller : " + user);

        userRepository.save(user);

        // Generate JWT immediately after registration
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return new LoginResponseDTO(token);
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).get();
        String token = jwtUtil.generateToken(user.getId(), request.getEmail());

        return new LoginResponseDTO(token);
    }
}