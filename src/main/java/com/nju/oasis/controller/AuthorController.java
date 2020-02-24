package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.AuthorVO;
import com.nju.oasis.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/detail")
    @ResponseBody
    public AuthorVO getAuthorDetail(@RequestParam("id") int id){
        return authorService.getAuthorDetail(id);
    }

}
