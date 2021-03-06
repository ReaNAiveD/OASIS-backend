package com.nju.oasis.service.impl;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.Field;
import com.nju.oasis.repository.AffiliationRepository;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.repository.FieldRepository;
import com.nju.oasis.service.FieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldServiceImpl implements FieldService {

    private DocumentRepository documentRepository;
    private AuthorRepository authorRepository;
    private AffiliationRepository affiliationRepository;

    @Autowired
    private FieldRepository fieldRepository;

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

    @Override
    public Optional<Field> findById(int id) {
        return fieldRepository.findById(id);
    }

    @Override
    public Page<DocumentVO> getDocumentsOfField(int fieldId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Document> documentPage = documentRepository.getAllByFieldId(fieldId, pageable);
        Page<DocumentVO> documentVOPage = documentPage.map(this::documentVOToDO);
        return documentVOPage;
    }

    private DocumentVO documentVOToDO(Document document){
        DocumentVO documentVO = new DocumentVO();
        BeanUtils.copyProperties(document, documentVO);
        List<Author> authorList = authorRepository.getAuthorsByDocumentId(document.getId());
        documentVO.setAuthors(authorList);
        return documentVO;
    }

    @Override
    public List<FieldRepository.HotFieldItem> getHotFields() {
        return fieldRepository.findHotField();
    }

    @Override
    public FieldRepository.FieldInfo getFieldInfo(int id) {
        return fieldRepository.getFieldInfo(id);
    }
}
