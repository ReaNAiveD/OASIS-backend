package com.nju.oasis.service;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.RefArticle;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.repository.RefArticleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    RefArticleRepository refArticleRepository;

    public DocumentVO getDocumentDetail(int documentId){
        DocumentVO documentVO = new DocumentVO();
        Document document = documentRepository.findById(documentId);
        BeanUtils.copyProperties(document, documentVO);
        List<Integer> authorIds = documentRepository.getAuthorsByDocumentId(documentId);
        List<Author> authorList = authorRepository.findAllById(authorIds);
        documentVO.setAuthors(authorList);
        List<RefArticle> refArticles = refArticleRepository.findAllByDocumentId(documentId);
        documentVO.setRef(refArticles);
        return documentVO;
    }

    public DocumentVO getDocumentListItem(int documentId){
        DocumentVO documentVO = new DocumentVO();
        Document document = documentRepository.findById(documentId);
        BeanUtils.copyProperties(document, documentVO);
        List<Integer> authorIds = documentRepository.getAuthorsByDocumentId(documentId);
        List<Author> authorList = authorRepository.findAllById(authorIds);
        documentVO.setAuthors(authorList);
        return documentVO;
    }
}
