package com.nju.oasis.service;

import com.nju.oasis.controller.VO.ResultVO;

public interface FieldService {

    ResultVO getFieldDocByYear(int id);

    ResultVO getFieldAuthorActivation(int id);

    ResultVO getFieldAffiliationActivation(int id);

}
