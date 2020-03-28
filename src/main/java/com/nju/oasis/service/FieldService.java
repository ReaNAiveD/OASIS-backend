package com.nju.oasis.service;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.Field;

import java.util.Optional;

public interface FieldService {

    Optional<Field> findById(int id);

    ResultVO getFieldDocByYear(int id);

    ResultVO getFieldAuthorActivation(int id);

    ResultVO getFieldAffiliationActivation(int id);

}
