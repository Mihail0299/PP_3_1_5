package com.example.pp_3_1_5.service;

import com.example.pp_3_1_5.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll ();
    User getById(Long id);
    User passwordCoder(User user);
    void save(User user);
    void deleteById(Long id);
    User findByUsername(String username);
    void update(User user);

}
