package com.example.demo.payLoad;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
