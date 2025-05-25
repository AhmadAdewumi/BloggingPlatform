package com.ahmad.BloggingPlatform.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateUserRequest {
    private String name;
    private String username;
    private LocalDateTime updatedAt;
}
