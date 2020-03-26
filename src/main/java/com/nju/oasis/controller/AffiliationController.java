package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aff")
public class AffiliationController {

    @Autowired
    private AffiliationService affiliationService;

    @GetMapping("/info")
    public ResultVO getBasicInfo(@RequestParam("id") int id){
        return affiliationService.getBasicInfo(id);
    }

    @GetMapping("/field/stat")
    public ResultVO getDocumentCountByField(@RequestParam("id")int id) {
        return affiliationService.getDocumentCountByField(id);
    }

    @GetMapping("/author/stat")
    public ResultVO getDocumentCountByAuthor(@RequestParam("id")int id) {
        return affiliationService.getDocumentCountByAuthor(id);
    }

}
