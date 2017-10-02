package com.ty.api.service;

import com.ty.api.entity.User;

import java.util.List;

public interface UserService {

    User findById(int id);

    User findByUsername(String name);

    List<User> findAll();
}
