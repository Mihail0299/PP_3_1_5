package com.example.pp_3_1_5.service;

import com.example.pp_3_1_5.dao.UserDao;
import com.example.pp_3_1_5.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findUserByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user.get();
    }
}