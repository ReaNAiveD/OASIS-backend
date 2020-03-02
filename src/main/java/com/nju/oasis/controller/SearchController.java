package com.nju.oasis.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.form.SearchForm;
import com.nju.oasis.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/26
 * @description:
 */
@Controller
public class SearchController {
    @Autowired
    SearchService searchService;

    @PostMapping("/document/fetch")
    @ResponseBody
    public Page<DocumentVO> search(@RequestBody String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        SearchForm searchForm = JSONObject.toJavaObject(jsonObject, SearchForm.class);
        if(jsonObject.containsKey("abstract")){
            searchForm.setMyAbstract(jsonObject.getString("abstract"));
        }
        //默认页大小
        if(searchForm.getPageSize()==0){
            searchForm.setPageSize(20);
        }
        if(StringUtils.isEmpty(searchForm.getOrderBy())){
            searchForm.setOrderBy("default");
        }
        if(StringUtils.isEmpty(searchForm.getConference())){
            searchForm.setConference("ase");
        }

        return searchService.searchDocument(searchForm);
    }
}
