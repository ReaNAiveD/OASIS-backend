package com.nju.oasis.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.oasis.domain.Document;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/20
 * @description:
 */
@Component
public class DbLoader implements CommandLineRunner {

    final static String filepath1 = "D:\\SoftwareProjects\\PycharmProjects\\MachineLearning\\oasis-spider\\ase_res.json";
    final static String filepath2 = "D:\\SoftwareProjects\\PycharmProjects\\MachineLearning\\oasis-spider\\icse_res.json";

    AuthorRepository authorRepository;
    DocumentRepository documentRepository;


    @Autowired
    public DbLoader(AuthorRepository authorRepository, DocumentRepository documentRepository) {
        this.authorRepository = authorRepository;
        this.documentRepository = documentRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        loadData_1();
    }

    private void loadData_1() {
        JSONArray documentJsonArray = ReadUtil.readJsonFile(filepath1);
        documentJsonArray.addAll(ReadUtil.readJsonFile(filepath2));
        for(Object documentObject: documentJsonArray){
            JSONObject documentJson = (JSONObject) documentObject;
            Document document = JSONObject.toJavaObject(documentJson, Document.class);
            document.setDocuAbstract(documentJson.getString("abstract"));
//            System.out.println(document.toString());
        }
    }
}
