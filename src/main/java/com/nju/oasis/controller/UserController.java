package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.User;
import com.nju.oasis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/15
 * @description:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user-info")
    public ResultVO getUserInfo(@RequestParam("account") String account, @RequestParam("password") String password) {

        User user = userRepository.findByAccount(account);
        if (user == null) {
            return ResultVO.builder().result(1).message("用户不存在").build();
        }
        if (user.getPassword().equals(password)) {
            return ResultVO.builder().result(0).data(user).build();
        } else
            return ResultVO.builder().result(1).message("用户名密码不匹配").build();
    }
}
