package com.nju.oasis;

import com.nju.oasis.domain.Author;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.service.DocumentService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    void contextLoads() {
    }

    @Ignore
    @Test
    void testDocument_1(){
        documentRepository.insertDocuAuthorRel(1,2);
    }

    @Ignore
    @Test
    void testDocument_2(){
        List<Integer> documents = documentRepository.getDocumentsByAuthorId(10450);
        for(int i: documents){
            System.out.println(i);
        }
    }

    @Test
    void testDocument_3(){
        System.out.println(documentService.getDocumentDetail(4327));
    }

    @Ignore
    @Test
    void testAuthor_1(){
        Author author = authorRepository.findById(10450);
        System.out.println(author);
    }

}
