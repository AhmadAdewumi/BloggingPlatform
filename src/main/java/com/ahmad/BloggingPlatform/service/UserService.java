package com.ahmad.BloggingPlatform.service;

import com.ahmad.BloggingPlatform.exception.AlreadyExistException;
import com.ahmad.BloggingPlatform.exception.ResourceNotFoundException;
import com.ahmad.BloggingPlatform.model.User;
import com.ahmad.BloggingPlatform.repository.UserRepository;
import com.ahmad.BloggingPlatform.request.CreateUserRequest;
import com.ahmad.BloggingPlatform.request.UpdateUserRequest;
import com.ahmad.BloggingPlatform.response.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        /*check if the user with this email already exists, it might be null , so wrap inside optional or just use the existsbyemail inside repository, if
        true, tell user to use another email that is return,if false , proceed,
       */
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setName(request.getName());
                    user.setEmail(request.getEmail());
                    user.setUsername(request.getUsername());
                    user.setCreatedAt(LocalDateTime.now());

                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistException("Oops , Account with email " + request.getEmail() + "Already exists"));
    }

    @Override
    public User updateUserById(UpdateUserRequest request , long userId) {
        return userRepository.findById(userId).map(existingUser ->{
            existingUser.setName(request.getName());
            existingUser.setUsername(request.getUsername());
            existingUser.setUpdatedAt(LocalDateTime.now());

            return userRepository.save(existingUser);

        }).orElseThrow(() -> new ResourceNotFoundException("User with Id " + userId + " Not Found!"));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id " + userId + " Not Found!"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " Not Found!"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " Not Found!") );
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete , () -> new ResourceNotFoundException("User with " + userId + " Not Found!"));
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user , UserDto.class);
    }
}
