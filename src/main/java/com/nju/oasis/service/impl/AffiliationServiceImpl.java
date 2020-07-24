package com.nju.oasis.service.impl;

import com.nju.oasis.controller.VO.AffiliationInfoVO;
import com.nju.oasis.controller.VO.CooperateAffiliationVO;
import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.Affiliation;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.statistics.AffiliationStatistics;
import com.nju.oasis.repository.AffiliationRepository;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.repository.statistics.AffiliationStatisticsRepository;
import com.nju.oasis.service.AffiliationService;
import com.nju.oasis.service.DocumentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AffiliationServiceImpl implements AffiliationService {

    @Autowired
    AffiliationRepository affiliationRepository;
    @Autowired
    AffiliationStatisticsRepository affiliationStatisticsRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentService documentService;

    @Override
    public ResultVO getBasicInfo(int id) {
        AffiliationInfoVO infoVO = new AffiliationInfoVO();
        Affiliation affiliation = affiliationRepository.findById(id);
        if (affiliation==null) return ResultVO.FAILED(ResultVO.AFFILIATION_NOT_EXIST, infoVO, "affiliation not exist");
        infoVO.setName(affiliation.getName());
        infoVO.setAuthorCount(affiliationRepository.authorCount(id));
        infoVO.setDocCount(affiliationRepository.documentCount(id));
        infoVO.setCitationCount(affiliationRepository.citationCount(id));
        return ResultVO.SUCCESS(infoVO);
    }

    @Override
    public ResultVO getDocumentCountByField(int id) {
        Affiliation affiliation = affiliationRepository.findById(id);
        if (affiliation==null) return ResultVO.FAILED(ResultVO.AFFILIATION_NOT_EXIST, "affiliation not exist");
        List<Map<String, String>> result = affiliationRepository.fieldStatistic(id);
        return ResultVO.SUCCESS(result);
    }

    @Override
    public ResultVO getAuthorActivation(int id) {
        List<Map<String, String>> result = affiliationRepository.authorActivationStatistic(id);
        return ResultVO.SUCCESS(result);
    }

    @Override
    public ResultVO getDocumentCountByAuthor(int id) {
        List<Map<String, String>> result = affiliationRepository.authorStatistic(id);
        return ResultVO.SUCCESS(result);
    }

    @Override
    public List<AffiliationStatistics> getAffiliationsWithMaxActivation(int num) {
        Pageable pageable = PageRequest.of(0, num, Sort.Direction.DESC, "activation");
        Page<AffiliationStatistics> firstPage = affiliationStatisticsRepository.findAll(pageable);
        return firstPage.getContent();
    }

    @Override
    public Page<DocumentVO> getDocumentsOfAff(int affId, int page, int pageSize) {
        List<Document> documentList = documentRepository.getAllByAffiliationId(affId, page, pageSize);
        List<DocumentVO> resultList = new ArrayList<>();
        for(Document document:documentList){
            DocumentVO documentVO = new DocumentVO();
            BeanUtils.copyProperties(document, documentVO);
            List<Author> authorList = authorRepository.getAuthorsByDocumentId(document.getId());
            documentVO.setAuthors(authorList);
            resultList.add(documentVO);
        }

        /*
        生成分页对象
         */
        int totalNum = documentRepository.countAllByAffiliationId(affId);
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<DocumentVO> resultPage = new PageImpl<>(resultList, pageable, totalNum);
        return resultPage;
    }

    @Override
    public ResultVO getCooperateAff(int affId) {
        List<CooperateAffiliationVO> cooperateAffVOList = new ArrayList<>();
        List<Map<String, Object>> repoList = affiliationRepository.cooperateAffiliations(affId);
        for(Map<String, Object> item: repoList){
            CooperateAffiliationVO affVO = new CooperateAffiliationVO();
            affVO.setAffiliationId(String.valueOf(item.get("affiliation_id")));
            affVO.setAffiliationName((String) item.get("affiliation_name"));
            affVO.setCooperateCount(String.valueOf(item.get("docu_count")));
            cooperateAffVOList.add(affVO);
        }
        cooperateAffVOList.sort((a1, a2)->{
            if(Integer.parseInt(a1.getCooperateCount())>Integer.parseInt(a2.getCooperateCount())){
                return -1;
            }
            else if(Integer.parseInt(a1.getCooperateCount())==Integer.parseInt(a2.getCooperateCount())){
                return 0;
            }
            else {
                return 1;
            }
        });
        return ResultVO.SUCCESS(cooperateAffVOList);
    }
}
