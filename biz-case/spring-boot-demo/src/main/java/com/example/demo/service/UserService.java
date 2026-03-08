package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService extends IService<User> {
    
    List<User> listAll();
    
    User getById(Long id);
    
    boolean saveUser(User user);
    
    boolean updateUser(User user);
    
    boolean deleteById(Long id);
    
    List<User> listByName(String name);
}
