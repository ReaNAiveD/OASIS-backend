package com.nju.oasislogin.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    int register(String username, String password);

    int renewPass(int id, String newPassword);

}
