package com.nju.oasis.controller;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.domain.statistics.AffiliationStatistics;
import com.nju.oasis.domain.statistics.AuthorStatistics;
import com.nju.oasis.service.AffiliationService;
import com.nju.oasis.service.AuthorService;
import com.nju.oasis.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    DocumentService documentService;
    @Autowired
    AffiliationService affiliationService;

    @GetMapping("/top/author")
    @ResponseBody
    public List<AuthorStatistics> getAuthorsWithMaxDocumentCount(@RequestParam(value = "num", defaultValue = "5") int num){
        return authorService.getAuthorsMaxDocumentCount(num);
    }

    @GetMapping("/top/document/max_download")
    @ResponseBody
    public List<DocumentVO> getDocumentsWithMaxDownloads(@RequestParam(value = "num", defaultValue = "5") int num){
        return documentService.getDocumentsWithMaxDownloads(num);
    }

    @GetMapping("/top/affiliation")
    @ResponseBody
    public List<AffiliationStatistics> getAffiliationsWithMaxActivation(@RequestParam(value = "num", defaultValue = "5")int num){
        return affiliationService.getAffiliationsWithMaxActivation(num);
    }

}
