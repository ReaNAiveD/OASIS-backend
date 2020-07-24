package com.nju.oasis.service;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.statistics.AffiliationStatistics;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AffiliationService {

    public ResultVO getBasicInfo(int id);

    public ResultVO getDocumentCountByField(int id);

    public ResultVO getDocumentCountByAuthor(int id);

    public ResultVO getAuthorActivation(int id);

    List<AffiliationStatistics> getAffiliationsWithMaxActivation(int num);

    Page<DocumentVO> getDocumentsOfAff(int affId, int page, int pageSize);

    public ResultVO getCooperateAff(int affId);

}
