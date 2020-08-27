package com.nju.oasislogin.service.impl;

import com.nju.oasislogin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void register() {
        userService.register("test1", "test");
    }

    @Test
    void renewPass() {
    }
}