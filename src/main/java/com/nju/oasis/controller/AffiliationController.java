package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aff")
public class AffiliationController {

    @Autowired
    private AffiliationService affiliationService;

    @RequestMapping("/info")
    public ResultVO getBasicInfo(@RequestParam("id") int id){
        return affiliationService.getBasicInfo(id);
    }

}
