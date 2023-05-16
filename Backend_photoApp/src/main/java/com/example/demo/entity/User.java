package com.example.demo.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user")
@Data // lombok
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String email;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    public User(String username, String fullName, String email, AuthProvider authProvider, String password) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.authProvider = authProvider;
        this.password = password;
    }

    public enum AuthProvider {
        LOCAL, FACEBOOK, GOOGLE
    }
}
