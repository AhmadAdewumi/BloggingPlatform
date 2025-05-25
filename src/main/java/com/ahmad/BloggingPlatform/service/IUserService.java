package com.ahmad.BloggingPlatform.service;

import com.ahmad.BloggingPlatform.model.User;
import com.ahmad.BloggingPlatform.request.CreateUserRequest;
import com.ahmad.BloggingPlatform.request.UpdateUserRequest;
import com.ahmad.BloggingPlatform.response.UserDto;

public interface IUserService {
    User createUser(CreateUserRequest request);
    User updateUserById(UpdateUserRequest request , long id);
    User getUserById(Long id);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    void deleteUserById(Long id);
    UserDto convertToDto(User user);
}
