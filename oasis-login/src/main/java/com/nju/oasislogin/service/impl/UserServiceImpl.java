package com.nju.oasislogin.service.impl;

import com.nju.oasislogin.domain.User;
import com.nju.oasislogin.repository.UserRepository;
import com.nju.oasislogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public int register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public int renewPass(int id, String newPassword) {
        return 0;
    }
}
