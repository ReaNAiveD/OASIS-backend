package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping("/detail")
    DocumentVO getDocumentDetail(@RequestParam("id") int documentId){
        return documentService.getDocumentDetail(documentId);
    }
}
