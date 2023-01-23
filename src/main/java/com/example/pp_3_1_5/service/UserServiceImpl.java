package com.example.pp_3_1_5.service;

import com.example.pp_3_1_5.dao.UserDao;
import com.example.pp_3_1_5.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;



@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDAO,  PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User getById(Long id) {
        return userDAO.getById(id);
    }

    @Transactional
    @Override
    public void save(User user) {
        userDAO.save(passwordCoder(user));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findUserByUsername(username).get();
    }

    @Override
    public void update(User user) {
        userDAO.save(user);
    }
}
