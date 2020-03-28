package com.nju.oasis.service.impl;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldServiceImpl implements FieldService {

    private DocumentRepository documentRepository;

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    @Override
    public ResultVO getFieldDocByYear(int id) {
        return ResultVO.SUCCESS(documentRepository.findDocCountYearSummaryByField(id));
    }
}
