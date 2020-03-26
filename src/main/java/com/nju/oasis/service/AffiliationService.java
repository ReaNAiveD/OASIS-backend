package com.nju.oasis.service;

import com.nju.oasis.controller.VO.ResultVO;
import org.springframework.stereotype.Service;

public interface AffiliationService {

    public ResultVO getBasicInfo(int id);

    public ResultVO getDocumentCountByField(int id);

    public ResultVO getDocumentCountByAuthor(int id);

    public ResultVO getAuthorActivation(int id);

}
