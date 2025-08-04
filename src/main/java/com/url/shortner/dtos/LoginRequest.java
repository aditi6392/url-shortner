package com.url.shortner.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Login for existing users")
public class LoginRequest {

    @Schema(description = "User Name",example = "Aditi")
    private String username;
    @Schema(description = "Password",example = "aditi123")
    private String password;
}
