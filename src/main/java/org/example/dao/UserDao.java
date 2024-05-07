package org.example.dao;

import org.example.entity.User;

public interface UserDao {
    User findByUsername(String username);
    User save(User user);
}