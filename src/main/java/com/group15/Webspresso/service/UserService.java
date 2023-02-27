package com.group15.Webspresso.service;

import java.util.List;

import com.group15.Webspresso.entity.User;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(User user);

    User getUserById(int id);

    User updateUser(User user);
    
    void deleteUserById(int id);

    User findByUsernameAndPassword(String email, String password);

    void save(User user);
}
