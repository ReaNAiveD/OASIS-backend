package com.nju.oasis.service;

import com.nju.oasis.controller.VO.ResultVO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliationService {

    public ResultVO getBasicInfo(int id);

}
