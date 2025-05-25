package com.ahmad.BloggingPlatform.request;

import com.ahmad.BloggingPlatform.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CreatePostRequest {
    private String title;
    private String content;
    private String category;
    private Set<String> tags = new HashSet<>();
    private LocalDateTime createdAt;

}
