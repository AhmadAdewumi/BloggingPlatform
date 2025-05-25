package com.ahmad.BloggingPlatform.controller;

import com.ahmad.BloggingPlatform.exception.AlreadyExistException;
import com.ahmad.BloggingPlatform.exception.ResourceNotFoundException;
import com.ahmad.BloggingPlatform.model.User;
import com.ahmad.BloggingPlatform.request.CreateUserRequest;
import com.ahmad.BloggingPlatform.request.UpdateUserRequest;
import com.ahmad.BloggingPlatform.response.ApiResponseBody;
import com.ahmad.BloggingPlatform.response.UserDto;
import com.ahmad.BloggingPlatform.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseBody> createUser(@Valid @RequestBody CreateUserRequest request){
        try {
            User theUser = userService.createUser(request);
            UserDto convertedUser = userService.convertToDto(theUser);
            return ResponseEntity.ok(new ApiResponseBody("User created Successfully", convertedUser));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseBody("Email already exists" , null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseBody> updateUserById(@Valid @RequestBody UpdateUserRequest request , @PathVariable long id){
        try {
            User theUser = userService.updateUserById(request, id);
            UserDto convertedUser = userService.convertToDto(theUser);
            return ResponseEntity.ok(new ApiResponseBody("User with ID " + id + " Created Successfully" , convertedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody("User with id " + id + " Not Found" , null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseBody> getUserById(@PathVariable long id){
        try {
            User theUser = userService.getUserById(id);
            UserDto convertedUser = userService.convertToDto(theUser);
            return ResponseEntity.ok(new ApiResponseBody("User with ID " + id + " Retrieved Successfully" , convertedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody("User with id " + id + " Not Found" , null));
        }
    }

    @GetMapping("/email")
    public ResponseEntity<ApiResponseBody> getUserById(@RequestParam("email") String email){
        try {
            User theUser = userService.getUserByEmail(email);
            UserDto convertedUser = userService.convertToDto(theUser);
            return ResponseEntity.ok(new ApiResponseBody("User with email " + email + " Retrieved Successfully" , convertedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody("User with email " + email + " Not Found" , null));
        }
    }

    @GetMapping("/username")
    public ResponseEntity<ApiResponseBody> getUserByUsername(@RequestParam("username") String username){
        try {
            User theUser = userService.getUserByUsername(username);
            UserDto convertedUser = userService.convertToDto(theUser);
            return ResponseEntity.ok(new ApiResponseBody("User with username " + username + " Retrieved Successfully" , convertedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody("User with username " + username + " Not Found" , null));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseBody> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok(new ApiResponseBody("User with user-id " + userId + " Deleted Successfully" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody("User with user - id " + userId + " Not Found" , null));
        }
    }



}
