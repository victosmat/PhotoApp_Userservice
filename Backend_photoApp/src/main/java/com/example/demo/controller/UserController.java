package com.example.demo.controller;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtAuthenticationFilter;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.payLoad.LoginRequest;
import com.example.demo.payLoad.LoginResponse;
import com.example.demo.payLoad.Message;
import com.example.demo.payLoad.dto.UserDTO;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getUsersByFollowing")
    public ResponseEntity<List<User>> getUsersByFollowing(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        User user = userService.getUserFromJWT(token);
        List<User> users = userService.getUserByFollowing(user.getId());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUsersByFollowed")
    public ResponseEntity<List<User>> getUsersByFollowed(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        User user = userService.getUserFromJWT(token);
        List<User> users = userService.getUsersByFollowed(user.getId());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUserFromJWT")
    public ResponseEntity<User> getUserFromJWT(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        User user = userService.getUserFromJWT(token);
//        System.out.println("Authorization header: " + httpServletRequest.getHeader("Authorization"));
//        System.out.println("Decoded user from JWT: " + user);
        if (user == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Message> updateUser(@RequestBody User user, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        User user1 = userService.updateUser(user, token);
        Message message = new Message();
        if (user1 == null) message.setMessage("Error: Update user failed!");
        else message.setMessage("Success: Update user successfully!");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/getUsersByKeyword/{keyword}")
    public ResponseEntity<List<User>> getUsersByKeyword(@PathVariable String keyword, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        List<User> users = userService.getUsersByKeyword(keyword, token);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<Message> follow(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        User user = userService.getUserFromJWT(token);
        Follow follow = userService.follow(id, user.getId());
        if (follow == null) return ResponseEntity.badRequest().body(new Message("Error: Follow failed!"));
        else return ResponseEntity.ok(new Message("Success: Follow successfully!"));
    }

    @PostMapping("/unfollow/{id}")
    public ResponseEntity<Message> unfollow(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        User user = userService.getUserFromJWT(token);
        Follow follow = userService.unfollow(id, user.getId());
        if (follow == null) return ResponseEntity.badRequest().body(new Message("Error: Unfollow failed!"));
        else return ResponseEntity.ok(new Message("Success: Unfollow successfully!"));
    }

    @GetMapping("/getFollowing/{id}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Long id) {
        List<User> users = userService.getFollowing(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getFollowed/{id}")
    public ResponseEntity<List<User>> getFollowed(@PathVariable Long id) {
        List<User> users = userService.getFollowed(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/checkFollow/{id}")
    public ResponseEntity<Message> checkFollow(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        User user = userService.getUserFromJWT(token);
        Boolean check = userService.checkFollow(id, user.getId());
        if (check) return ResponseEntity.ok(new Message("Success"));
        else return ResponseEntity.ok(new Message("Failed"));
    }

}