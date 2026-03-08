package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Override
    public List<User> listAll() {
        return list();
    }
    
    @Override
    public User getById(Long id) {
        return baseMapper.selectById(id);
    }
    
    @Override
    public boolean saveUser(User user) {
        return save(user);
    }
    
    @Override
    public boolean updateUser(User user) {
        return updateById(user);
    }
    
    @Override
    public boolean deleteById(Long id) {
        return removeById(id);
    }
    
    @Override
    public List<User> listByName(String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getName, name);
        return list(wrapper);
    }
}
