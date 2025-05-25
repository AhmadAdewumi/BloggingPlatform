package com.ahmad.BloggingPlatform.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
    private String category;
    private Set<String> tags = new HashSet<>();
    private LocalDateTime updatedAt;
}
