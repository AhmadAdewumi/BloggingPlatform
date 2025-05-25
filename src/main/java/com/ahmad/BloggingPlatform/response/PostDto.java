package com.ahmad.BloggingPlatform.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {
    private String title;
    private String content;
    private String category;
    private Set<String> tags = new HashSet<>();
    private LocalDateTime createdAt;

}
