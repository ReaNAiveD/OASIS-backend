package com.nju.oasis.service.impl;

import com.nju.oasis.controller.VO.AffiliationInfoVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.Affiliation;
import com.nju.oasis.repository.AffiliationRepository;
import com.nju.oasis.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AffiliationServiceImpl implements AffiliationService {

    @Autowired
    AffiliationRepository affiliationRepository;

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
}
