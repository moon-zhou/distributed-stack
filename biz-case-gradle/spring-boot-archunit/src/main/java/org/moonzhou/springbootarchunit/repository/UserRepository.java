package org.moonzhou.springbootarchunit.repository;

import org.moonzhou.springbootarchunit.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    
    private final List<User> users = new ArrayList<>();
    
    public List<User> findAll() {
        return users;
    }
    
    public Optional<User> findById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }
    
    public User save(User user) {
        users.add(user);
        return user;
    }
    
    public void deleteById(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}