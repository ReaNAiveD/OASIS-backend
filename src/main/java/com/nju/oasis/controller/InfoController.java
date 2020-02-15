package com.nju.oasis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/15
 * @description:
 */
@Controller
public class InfoController {

    @GetMapping("/info")
    public String info(){
        return "info";
    }

    @GetMapping("/data")
    public String data(){
        return "data";
    }
}
