package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/field")
public class FieldController {

    private FieldService fieldService;

    @Autowired
    public void setFieldService(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/publication/year")
    public ResultVO getFieldPublicationByYear(@RequestParam("id") int id){
        return fieldService.getFieldDocByYear(id);
    }

    @GetMapping("/author/activation")
    public ResultVO getAuthorActivationByField(@RequestParam("id") int id){
        return fieldService.getFieldAuthorActivation(id);
    }

    @GetMapping("/aff/activation")
    public ResultVO getAffiliationActivationByField(@RequestParam("id") int id){
        return fieldService.getFieldAffiliationActivation(id);
    }

}
