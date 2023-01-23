package com.example.pp_3_1_5.dao;

import com.example.pp_3_1_5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String name);
}
