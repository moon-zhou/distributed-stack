package org.moonzhou.springbootarchunit.controller;

import org.moonzhou.springbootarchunit.model.User;
import org.moonzhou.springbootarchunit.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User controller for managing user resources
 * Local test path: http://localhost:8080/users
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Get all users
     * Local test path: GET http://localhost:8080/users
     * @return list of all users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    /**
     * Get user by id
     * Local test path: GET http://localhost:8080/users/{id}
     * Example: GET http://localhost:8080/users/1
     * @param id user id
     * @return user with the specified id
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    /**
     * Create a new user
     * Local test path: POST http://localhost:8080/users
     * Request body example: {"name": "John Doe", "email": "john.doe@example.com"}
     * @param user user to create
     * @return created user
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
    
    /**
     * Delete user by id
     * Local test path: DELETE http://localhost:8080/users/{id}
     * Example: DELETE http://localhost:8080/users/1
     * @param id user id to delete
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}