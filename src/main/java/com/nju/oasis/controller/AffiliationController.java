package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.service.AffiliationService;
import com.nju.oasis.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aff")
public class AffiliationController {

    @Autowired
    private AffiliationService affiliationService;

    @Autowired
    private DocumentService documentService;

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

    @GetMapping("/author/activation")
    public ResultVO getAuthorActivation(@RequestParam("id")int id){
        return affiliationService.getAuthorActivation(id);
    }

    @GetMapping("/document")
    public Page<DocumentVO> getDocumentsOfAff(@RequestParam("id") int affId,
                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        return affiliationService.getDocumentsOfAff(affId, page, pageSize);

    }

    @GetMapping("/cooperate/aff")
    public ResultVO getCooperateAffiliations(@RequestParam("id") int affId){
        return affiliationService.getCooperateAff(affId);
    }

}
