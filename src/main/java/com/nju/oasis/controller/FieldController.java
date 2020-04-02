package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.Field;
import com.nju.oasis.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/field")
public class FieldController {

    private FieldService fieldService;

    @Autowired
    public void setFieldService(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/detail")
    public ResultVO getFieldDetail(@RequestParam("id") int id){
        Optional<Field> field = fieldService.findById(id);
        if(field.isPresent()){
            return ResultVO.builder().result(0).data(field.get()).build();
        }
        else {
            return ResultVO.builder().result(1).message("该领域不存在").build();
        }
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

    @GetMapping("/document")
    public Page<DocumentVO> getDocumentsOfField(@RequestParam("id") int fieldId,
                                                @RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        return fieldService.getDocumentsOfField(fieldId, page, pageSize);
    }

    @GetMapping("/hot")
    public ResultVO getHotFields(){
        return ResultVO.SUCCESS(fieldService.getHotFields());
    }

}
