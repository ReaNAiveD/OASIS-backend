package com.nju.oasis;

import com.nju.oasis.repository.DocumentRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OasisApplicationTests {

    @Autowired
    DocumentRepository documentRepository;

    @Test
    void contextLoads() {
    }

    @Ignore
    @Test
    void testDocument_1(){
        documentRepository.insertDocuAuthorRel(1,2);
    }
}
