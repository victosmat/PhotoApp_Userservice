package com.example.demo.service;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.payLoad.LoginResponse;
import com.example.demo.payLoad.dto.AuthDTO;
import com.example.demo.payLoad.dto.FacebookUserModel;
import com.example.demo.payLoad.dto.UserDTO;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.util.Constant;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private static final String CLIENT_ID = "603561517127-1ukbjrd0ae292be5n3ndl84m8f15td10.apps.googleusercontent.com";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private WebClient webClient;

//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        // Kiểm tra xem user có tồn tại trong database không?
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new CustomUserDetails(user);
//    }

    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id : " + userId);
        }
        return new CustomUserDetails(user);
    }

    public List<User> getUserByFollowing(Long id) {
        List<Follow> follows = followRepository.findAllByFollowingUserId(id);
        List<User> users = new ArrayList<>();
        for (Follow x : follows) users.add(userRepository.findById(x.getFollowedUserId()).get());
        return users;
    }

    public List<User> getUsersByFollowed(Long id) {
        List<Follow> follows = followRepository.findAllByFollowedUserId(id);
        List<User> users = new ArrayList<>();
        for (Follow x : follows) users.add(userRepository.findById(x.getFollowingUserId()).get());
        return users;
    }

    public User getUserFromJWT(String bearerToken) {
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
        return userRepository.findById(jwtTokenProvider.getUserIdFromJWT(bearerToken)).get();
//        return null;
    }

    public User updateUser(User user, String bearerToken) {
        User user1 = userRepository.findById(jwtTokenProvider.getUserIdFromJWT(bearerToken)).get();
        if (user.getUsername().equals(user1.getUsername())) {
            user1.setFullName(user.getFullName());
            user1.setAddress(user.getAddress());
            user1.setEmail(user.getEmail());
            return userRepository.save(user1);
        } else if (userRepository.existsByUsername(user.getUsername()))
            return null;
        else {
            user1.setFullName(user.getFullName());
            user1.setAddress(user.getAddress());
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            return userRepository.save(user1);
        }
    }

    public List<User> getUsersByKeyword(String keyword, String bearerToken) {
        User user = userRepository.findById(jwtTokenProvider.getUserIdFromJWT(bearerToken)).get();
        List<User> users = new ArrayList<>();
        if (keyword.equals(" ")) users = userRepository.findAll();
        else users = userRepository.findUsersByKeywords(keyword);
        for (User x : users) {
            if (x.getId() == user.getId()) {
                users.remove(x);
                break;
            }
        }
        return users;
    }

    public Follow follow(Long idFollowed, Long idFollowing) {
        Follow follow = followRepository.findByFollowingUserIdAndFollowedUserId(idFollowing, idFollowed);
        if (follow != null) return null;
        else {
            follow = new Follow();
            follow.setFollowedUserId(idFollowed);
            follow.setFollowingUserId(idFollowing);
            return followRepository.save(follow);
        }
    }

    public Follow unfollow(Long idFollowed, Long idFollowing) {
        Follow follow = followRepository.findByFollowingUserIdAndFollowedUserId(idFollowing, idFollowed);
        if (follow == null) return null;
        else {
            followRepository.delete(follow);
            return follow;
        }
    }

    public List<User> getFollowing(Long id) {
        List<Follow> follows = followRepository.findAllByFollowingUserId(id);
        List<User> users = new ArrayList<>();
        for (Follow x : follows) users.add(userRepository.findById(x.getFollowedUserId()).get());
        return users;
    }

    public List<User> getFollowed(Long id) {
        List<Follow> follows = followRepository.findAllByFollowedUserId(id);
        List<User> users = new ArrayList<>();
        for (Follow x : follows) users.add(userRepository.findById(x.getFollowingUserId()).get());
        return users;
    }

    public Boolean checkFollow(Long idFollowed, Long idFollowing) {
        Follow follow = followRepository.findByFollowingUserIdAndFollowedUserId(idFollowing, idFollowed);
        if (follow == null) return false;
        else return true;
    }

    public LoginResponse google(String accessToken) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(accessToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                String username = email.split("@")[0];
                User user = new User(null, username, passwordEncoder.encode(""), name, "", email, User.AuthProvider.GOOGLE);
                userRepository.save(user);
                final CustomUserDetails userPrincipal = new CustomUserDetails(user);
                String jwt = jwtTokenProvider.generateToken(userPrincipal);
                return new LoginResponse(jwt);
            } else {
                final User user = userOptional.get();
                if ((user.getAuthProvider() != User.AuthProvider.GOOGLE)) {
                    return new LoginResponse(null);
                }
                final CustomUserDetails userPrincipal = new CustomUserDetails(user);
                String jwt = jwtTokenProvider.generateToken(userPrincipal);
                return new LoginResponse(jwt);
            }

        } else {
            System.out.println("Invalid ID token.");
            return new LoginResponse(null);
        }
    }

    public ResponseEntity<?> facebook(String accessToken) {
        System.out.println(accessToken);
        String templateUrl = String.format(Constant.FACEBOOK_AUTH_URL, accessToken);
        log.info(templateUrl);
        FacebookUserModel facebookUserModel = webClient.get().uri(templateUrl).retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    throw new ResponseStatusException(clientResponse.statusCode(), "facebook login error");
                })
                .bodyToMono(FacebookUserModel.class)
                .block();

        final Optional<User> userOptional = userRepository.findByEmail(facebookUserModel.getEmail());

        if (userOptional.isEmpty()) {        //we have no user with given email so register them
            String email = facebookUserModel.getEmail();
            int index = email.indexOf("@");
            String username = index == -1 ? email : email.substring(0, index);

            String fullname = facebookUserModel.getFirstName() + " " + facebookUserModel.getLastName();

            final User user = new User(username, fullname, facebookUserModel.getEmail(), User.AuthProvider.FACEBOOK, "");
            userRepository.save(user);
            final CustomUserDetails userPrincipal = new CustomUserDetails(user);
            String jwt = jwtTokenProvider.generateToken(userPrincipal);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/v1/user/{username}")
                    .buildAndExpand(facebookUserModel.getFirstName()).toUri();

            return ResponseEntity.created(location).body(new LoginResponse(jwt));
        } else { // user exists just login
            final User user = userOptional.get();
            if ((user.getAuthProvider() != User.AuthProvider.FACEBOOK)) { //check if logged in with different logged in method
                log.info("BAD REQUEST");
                return ResponseEntity.badRequest().body("previously logged in with different login method");
            }

            CustomUserDetails userPrincipal = new CustomUserDetails(user);
            String jwt = jwtTokenProvider.generateToken(userPrincipal);
            return ResponseEntity.ok(new LoginResponse(jwt));
        }
    }

    public AuthDTO validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token))
            throw new RuntimeException("Invalid JWT token");

        Optional<User> userOptional = userRepository.findById(jwtTokenProvider.getUserIdFromJWT(token));

        if (userOptional.isEmpty()) {
//            throw new AppException("User not found", HttpStatus.NOT_FOUND);
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        return new AuthDTO(user.getId(), user.getUsername(), token);
    }
}
