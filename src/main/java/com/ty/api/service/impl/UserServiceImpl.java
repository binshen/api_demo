package com.ty.api.service.impl;

import com.ty.api.entity.User;
import com.ty.api.mapper.UserRepository;
import com.ty.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
