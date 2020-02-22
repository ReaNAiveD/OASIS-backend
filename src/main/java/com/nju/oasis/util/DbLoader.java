package com.nju.oasis.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.RefArticle;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.repository.RefArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;


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
    RefArticleRepository refArticleRepository;


    @Autowired
    public DbLoader(AuthorRepository authorRepository,
                    DocumentRepository documentRepository,
                    RefArticleRepository refArticleRepository) {
        this.authorRepository = authorRepository;
        this.documentRepository = documentRepository;
        this.refArticleRepository = refArticleRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        //加载数据库
//        new Thread(this::loadData_1).start();
    }

    private void loadData_1() {

        int count = 0;


//        清除表格
        authorRepository.deleteAll();
        documentRepository.deleteAll();
        documentRepository.deleteTableDocumentAuthor();
        refArticleRepository.deleteAll();

        JSONArray documentJsonArray = ReadUtil.readJsonFile(filepath1);
        documentJsonArray.addAll(ReadUtil.readJsonFile(filepath2));
        for (Object documentObject : documentJsonArray) {
            /*
            在遇到过长不合法属性的时候继续工作
             */
            count++;
//            //拦截器
//            if(count<501){
//                continue;
//            }
            System.out.println(count + ": document processed.");
            try {
                processDocument(documentObject);
            } catch (Exception e) {
                System.out.println("Exception occurred.");
                e.printStackTrace();
            }
        }
    }

    private void processDocument(Object documentObject) {
        JSONObject documentJson = (JSONObject) documentObject;

            /*
            生成文档信息
             */
        Document document = JSONObject.toJavaObject(documentJson, Document.class);//反射机制绑定属性
        document.setDocuAbstract(documentJson.getString("abstract"));
//            System.out.println(document.toString());
        Document savedDocument = documentRepository.save(document);
        //记录生成的id
        int documentId = savedDocument.getId();
            /*
            生成作者信息，但是可能为空
             */
        if (documentJson.containsKey("authors")) {
            JSONArray authorJsonArray = documentJson.getJSONArray("authors");
            //对于每个作者来说
            for (Object authorObject : authorJsonArray) {
                    /*
                    更新author到数据库
                     */
                JSONObject authorJson = (JSONObject) authorObject;
                //如果json数据中有id的话，就设上ieee_id
                if (authorJson.containsKey("id")) {
                    String ieeeId = authorJson.getString("id");
                    authorJson.remove("id");
                    authorJson.put("ieee_id", ieeeId);
                }
                Author author = JSONObject.toJavaObject(authorJson, Author.class);
                //将文章的作者关键字加给作者
                author.setAuthorKeywords(document.getAuthorKeywords());

                //插入author数据，如果有，那就更新
                Optional<Author> authorOptional = authorRepository.findAuthorByName(author.getName());
                int authorId = 0;
                if (authorOptional.isPresent()) {
                    Author authorTarget = authorOptional.get();
                    authorId = authorTarget.getId();
                    //生成新的作者关键字
                    String newAuthorKeywords = authorTarget.getAuthorKeywords() + ";" + author.getAuthorKeywords();
                    authorTarget.setAuthorKeywords(newAuthorKeywords);
                    authorRepository.save(authorTarget);
                } else {
                    Author savedAuthor = authorRepository.save(author);
                    authorId = savedAuthor.getId();
                }
//                    System.out.println(author.toString());
                    /*
                    添加文章-作者信息到数据库
                     */
                documentRepository.insertDocuAuthorRel(documentId, authorId);
            }
        }


            /*
            生成被引用文章信息
             */
        JSONArray refArticleJsonArray = documentJson.getJSONArray("ref");
        for (Object refArticleObject : refArticleJsonArray) {
            JSONObject refArticleJson = (JSONObject) refArticleObject;
            refArticleJson.remove("id");
            RefArticle refArticle = JSONObject.toJavaObject(refArticleJson, RefArticle.class);
            refArticle.setGoogleLink(refArticleJson.getString("googleScholarLink"));
            refArticle.setDocumentId(documentId);
            try {
                refArticleRepository.save(refArticle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
