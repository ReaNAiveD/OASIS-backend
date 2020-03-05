package com.nju.oasis;

import com.alibaba.fastjson.JSONObject;
import com.nju.oasis.controller.form.SearchForm;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.AuthorStatistics;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.AuthorStatisticsRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.service.AuthorService;
import com.nju.oasis.service.DocumentService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class OasisApplicationTests {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    DocumentService documentService;
    @Autowired
    AuthorService authorService;
    @Autowired
    AuthorStatisticsRepository authorStatisticsRepository;

    @Test
    void contextLoads() {
    }

//    @Ignore
//    @Test
//    void testDocument_1(){
//        documentRepository.insertDocuAuthorRel(1,2);
//    }

    @Test
    void testDocument_2(){
        List<Integer> documents = documentRepository.getDocumentsByAuthorId(10450);
        for(int i: documents){
            System.out.println(i);
        }
    }

    @Test
    void testDocument_3(){
//        System.out.println(documentService.getDocumentDetail(4327));
//        System.out.println(documentRepository.selectAllId());
//        JSONObject jsonObject = JSONObject.parseObject("{\"title\":\"\",\"author\":\"hello\", \"yearFrom\":null}");
//
//        SearchForm searchForm = JSONObject.toJavaObject(jsonObject, SearchForm.class);
//        System.out.println(searchForm);
        System.out.println(documentRepository.selectIdsByTitleLike("keynotes"));
    }


    @Test
    void testDocument_4(){
        List<Integer> idList = new ArrayList<>();
        idList.add(4320);
        idList.add(4321);
        Pageable pageable = PageRequest.of(0,1);
        System.out.println(documentRepository.findDocumentsByIdIn(idList, pageable).getContent());
    }

    @Test
    void testDocument_5(){

        System.out.println(documentService.getDocumentsWithMaxDownloads(5));
    }

    @Test
    void testAuthor_1(){
        Author author = authorRepository.findById(10450);
        System.out.println(author);
    }

    @Test
    void testAuthor_2(){
    }

    @Test
    void testAuthorStatistics_1(){
        AuthorStatistics authorStatistics = new AuthorStatistics();
        authorStatistics.setDocumentCount(8);
        authorStatisticsRepository.save(authorStatistics);
    }

    @Test
    void testAuthorStatistics_2(){
        List<AuthorStatistics> authorStatistics = authorService.getAuthorsMaxDocumentCount(5);
        System.out.println(authorStatistics);
    }

}
