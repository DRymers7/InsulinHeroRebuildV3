package com.insulinhero.serverv5.config.security;

import com.insulinhero.serverv5.config.security.login.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(Long userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role);

}
