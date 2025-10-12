package com.born.simplepos.springSimplePos.service;

import com.born.simplepos.springSimplePos.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getByEmail(String email);
}
