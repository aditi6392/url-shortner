package com.url.shortner.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
@Data
@Schema(description = "Registering new user")
public class RegisterRequest {

    @Schema(description = "User Name to be entered")
    private String username;
    @Schema(description = "Email to be entered")
    private String email;
    @Schema(description = "Role of user")
    private Set<String> role;
    @Schema(description = "Create password")
    private String password;
}
