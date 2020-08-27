package com.nju.oasislogin.service;

import org.springframework.stereotype.Service;

@Service
public interface SsoService {

    String login(String username, String password);

    int auth(String session);

}
