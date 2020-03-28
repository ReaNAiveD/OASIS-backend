package com.nju.oasis.service.impl;

import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.repository.AffiliationRepository;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldServiceImpl implements FieldService {

    private DocumentRepository documentRepository;
    private AuthorRepository authorRepository;
    private AffiliationRepository affiliationRepository;

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setAffiliationRepository(AffiliationRepository affiliationRepository) {
        this.affiliationRepository = affiliationRepository;
    }

    @Override
    public ResultVO getFieldDocByYear(int id) {
        return ResultVO.SUCCESS(documentRepository.findDocCountYearSummaryByField(id));
    }

    @Override
    public ResultVO getFieldAuthorActivation(int id) {
        return ResultVO.SUCCESS(authorRepository.getAuthorActivationByField(id));
    }

    @Override
    public ResultVO getFieldAffiliationActivation(int id) {
        return ResultVO.SUCCESS(affiliationRepository.fieldActivationStatistic(id));
    }
}
