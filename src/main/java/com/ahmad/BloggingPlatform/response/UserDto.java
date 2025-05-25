package com.ahmad.BloggingPlatform.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String name;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
