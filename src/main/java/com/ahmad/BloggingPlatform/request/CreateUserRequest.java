package com.ahmad.BloggingPlatform.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserRequest {
    private String name;
    @NotBlank
    private String email;
    private String username;
    private LocalDateTime createdAt;

}
