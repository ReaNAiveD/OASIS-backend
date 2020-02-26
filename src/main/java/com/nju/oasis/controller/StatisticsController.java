package com.nju.oasis.controller;

import com.nju.oasis.domain.AuthorStatistics;
import com.nju.oasis.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Controller
public class StatisticsController {
    @Autowired
    AuthorService authorService;

    @GetMapping("/top/author")
    public List<AuthorStatistics> getAuthorsWithMaxDocumentCount(int num){
        return authorService.getAuthorsMaxDocumentCount(num);
    }

}
