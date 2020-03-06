package com.nju.oasis.service;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.RefArticle;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.repository.RefArticleRepository;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
        List<Integer> authorIds = documentRepository.getAuthorsIdByDocumentId(documentId);
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
        List<Author> authorList = authorRepository.getAuthorsByDocumentId(document.getId());
        documentVO.setAuthors(authorList);
        return documentVO;
    }

    public List<DocumentVO> getDocumentsWithMaxDownloads(int num) {
        Pageable pageable = PageRequest.of(0, num, Sort.Direction.DESC, "totalDownload");

        Instant start = Instant.now();
        Page<Document> firstPage = documentRepository.findAll(pageable);
        Instant end = Instant.now();
        Metrics.summary("top_document_download_repository_summary").record(Duration.between(start, end).toMillis());

        start = Instant.now();
        List<DocumentVO> resultList = new ArrayList<>();
        for(Document document: firstPage.getContent()){
            Instant docItemStart = Instant.now();
            DocumentVO documentVO = new DocumentVO();
            BeanUtils.copyProperties(document, documentVO);
            List<Author> authorList = authorRepository.getAuthorsByDocumentId(document.getId());
            documentVO.setAuthors(authorList);
            resultList.add(documentVO);
            Instant docItemEnd = Instant.now();
            Metrics.summary("add_to_result_list").record(Duration.between(docItemStart, docItemEnd).toMillis());
        }
        end = Instant.now();
        Metrics.summary("add_to_result_list_total").record(Duration.between(start, end).toMillis());
        return resultList;
    }
}
