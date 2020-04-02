package com.nju.oasis.service;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.Field;
import org.springframework.data.domain.Page;
import com.nju.oasis.repository.FieldRepository;

import java.util.List;
import java.util.Optional;

public interface FieldService {

    Optional<Field> findById(int id);

    ResultVO getFieldDocByYear(int id);

    ResultVO getFieldAuthorActivation(int id);

    ResultVO getFieldAffiliationActivation(int id);

    /*
    根据领域id获取文章
     */
    Page<DocumentVO> getDocumentsOfField(int fieldId, int page, int pageSize);

    List<FieldRepository.HotFieldItem> getHotFields();

}
