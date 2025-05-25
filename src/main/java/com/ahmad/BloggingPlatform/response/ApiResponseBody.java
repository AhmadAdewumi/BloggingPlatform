package com.ahmad.BloggingPlatform.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponseBody {
    private String message;
    private Object data;
}
