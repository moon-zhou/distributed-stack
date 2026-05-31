package com.example.openspecdemo.controller;

import com.example.openspecdemo.generated.api.UsersApi;
import com.example.openspecdemo.generated.model.UserCreateRequest;
import com.example.openspecdemo.generated.model.UserResponse;
import com.example.openspecdemo.generated.model.UserUpdateRequest;
import com.example.openspecdemo.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponse> createUser(@Valid UserCreateRequest userCreateRequest) {
        com.example.openspecdemo.dto.user.UserCreateRequest serviceRequest = new com.example.openspecdemo.dto.user.UserCreateRequest();
        serviceRequest.setUsername(userCreateRequest.getUsername());
        serviceRequest.setPassword(userCreateRequest.getPassword());
        serviceRequest.setEmail(userCreateRequest.getEmail());

        com.example.openspecdemo.dto.user.UserResponse serviceResponse = userService.create(serviceRequest);
        return ResponseEntity.status(201).body(toGeneratedUser(serviceResponse));
    }

    @Override
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<UserResponse> users = userService.list().stream().map(this::toGeneratedUser).toList();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        return ResponseEntity.ok(toGeneratedUser(userService.get(id)));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, @Valid UserUpdateRequest userUpdateRequest) {
        com.example.openspecdemo.dto.user.UserUpdateRequest serviceRequest = new com.example.openspecdemo.dto.user.UserUpdateRequest();
        serviceRequest.setEmail(userUpdateRequest.getEmail());
        return ResponseEntity.ok(toGeneratedUser(userService.update(id, serviceRequest)));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponse toGeneratedUser(com.example.openspecdemo.dto.user.UserResponse user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}
