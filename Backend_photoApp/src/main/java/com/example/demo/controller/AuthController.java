package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.payLoad.LoginRequest;
import com.example.demo.payLoad.LoginResponse;
import com.example.demo.payLoad.Message;
import com.example.demo.payLoad.dto.AuthDTO;
import com.example.demo.payLoad.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }
    @PostMapping("/login/facebook")
    public ResponseEntity<?> facebook(@RequestBody Map<String,String> tokenMap ) {
        String accessToken = tokenMap.get("accessToken");
        return userService.facebook(accessToken);
    }
    @PostMapping("/login/google/{idToken}")
    public ResponseEntity<LoginResponse> google(@PathVariable String idToken) throws GeneralSecurityException, IOException {
        System.out.println("idToken = " + idToken);
        return new ResponseEntity<>(userService.google(idToken), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Message> registryUser(@RequestBody UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.badRequest().body(new Message("Error: Username is already taken!"));
        } else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setFullName(userDTO.getFullName());
            user.setAddress(userDTO.getAddress());
            user.setAuthProvider(User.AuthProvider.LOCAL);
            userRepository.save(user);
            return ResponseEntity.ok(new Message("Success: User registered successfully!"));
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<AuthDTO> validateToken(@RequestParam String token) {
        log.info("Trying to validate token {}", token);
        return ResponseEntity.ok(userService.validateToken(token));
    }
}
