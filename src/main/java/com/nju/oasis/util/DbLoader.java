package com.nju.oasis.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.oasis.domain.*;
import com.nju.oasis.domain.statistics.AuthorStatistics;
import com.nju.oasis.repository.*;
import com.nju.oasis.repository.statistics.AuthorStatisticsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;


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
    final static String acemap_field_path = "src/main/resources/sourcedata/FieldAcemap.csv";

    AuthorRepository authorRepository;
    DocumentRepository documentRepository;
    RefArticleRepository refArticleRepository;
    AuthorStatisticsRepository authorStatisticsRepository;
    FieldRepository fieldRepository;
    AffiliationRepository affiliationRepository;

    @Autowired
    public DbLoader(AuthorRepository authorRepository,
                    DocumentRepository documentRepository,
                    RefArticleRepository refArticleRepository,
                    AuthorStatisticsRepository authorStatisticsRepository,
                    FieldRepository fieldRepository,
                    AffiliationRepository affiliationRepository) {
        this.authorRepository = authorRepository;
        this.documentRepository = documentRepository;
        this.refArticleRepository = refArticleRepository;
        this.authorStatisticsRepository = authorStatisticsRepository;
        this.fieldRepository = fieldRepository;
        this.affiliationRepository = affiliationRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        //加载领域信息
//        new Thread(this::loadField).start();
        //加载数据库
//        new Thread(this::loadData_1).start();
        //更新document表
//        new Thread(this::updateDocument).start();
        //更新author表和载入affiliation表
//        new Thread(this::updateAuthor).start();
        //加载作者统计信息
//        new Thread(this::loadAuthorStatistics).start();
        //更新author_statistics
//        new Thread(this::updateAuthorStatistics).start();
        //更新document
//        new Thread(this::updateDocument_2).start();

    }

    /*
    该方法用于加载论文，作者，机构，引用文章
     */
    private void loadData_1() {

        int count = 0;


//        清除表格
        System.out.println("deleting table......");
        authorRepository.deleteAll();
        documentRepository.deleteAll();
        documentRepository.deleteTableDocumentAuthor();
        refArticleRepository.deleteAll();
        System.out.println("delete completed.");

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
            System.out.println(count+":已经处理了"+count/1400.0+"%的文章了，共1200");
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
        document.setTotalCitations(document.getTotalDownload()/50);
        //获取所有的分类信息
        List<Field> fieldList = fieldRepository.findAll();
        //为文章设置分类
        setFieldForDocument(fieldList, document);

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

                /*
                更新机构信息,并填充作者机构id
                 */
                if(!StringUtils.isEmpty(author.getAffiliation())){
                    Optional<Affiliation> affiliationOptional = affiliationRepository.findByName(author.getAffiliation());
                    if(affiliationOptional.isPresent()){
                        author.setAffiliationId(affiliationOptional.get().getId());
                    }
                    else{
                        //创建新的机构并填充id
                        Affiliation affiliation = new Affiliation();
                        affiliation.setName(author.getAffiliation());
                        int createdId = affiliationRepository.save(affiliation).getId();
                        author.setAffiliationId(createdId);
                    }
                }

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

    /*
    更新文章信息
     */
    private void updateDocument(){
        System.out.println("正在取出所有论文...");
        List<Document> documentList = documentRepository.findAll();
        //获取所有的分类信息
        List<Field> fieldList = fieldRepository.findAll();
        System.out.println("开始处理论文");
        int count = 0;
        for(Document document:documentList){
            count++;
            System.out.println(count+":已经处理了"+count/1400.0+"%的文章了，共1200");
            document.setTotalCitations(document.getTotalDownload()/50);
            setFieldForDocument(fieldList, document);
            documentRepository.save(document);
        }
    }

    private void updateAuthor(){
        System.out.println("正在加载author表...");
        List<Author> authorList = authorRepository.findAll();
        int count = 0;
        for(Author author:authorList){
            /*
                更新机构信息,并填充作者机构id
                 */
            System.out.println(++count+": 共" +authorList.size());
            if(!StringUtils.isEmpty(author.getAffiliation())){
                Optional<Affiliation> affiliationOptional = affiliationRepository.findByName(author.getAffiliation());
                if(affiliationOptional.isPresent()){
                    author.setAffiliationId(affiliationOptional.get().getId());
                }
                else{
                    //创建新的机构并填充id
                    Affiliation affiliation = new Affiliation();
                    affiliation.setName(author.getAffiliation());
                    int createdId = affiliationRepository.save(affiliation).getId();
                    author.setAffiliationId(createdId);
                }
            }
            authorRepository.save(author);
        }
    }

    private void setFieldForDocument(List<Field> fieldList, Document document){
        //去除括号
        String target = document.getKeywords().replaceAll("\\(","")
                .replaceAll("\\)","");
        //分割出单词
        List<String> documentKeywords = Arrays.asList(target.split(",|\\s+"));
        Boolean flag = false;
        for(Field field:fieldList){
            for(String core:field.getKeywords().split(",")){
                if(documentKeywords.contains(core)){
                    flag = true;
                    document.setFieldId(field.getId());
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        if (flag==false){
            //无法分辨出是哪个类的，随机指派
            int i = (int)(Math.random()*fieldList.size());
            document.setFieldId(fieldList.get(i).getId());
        }
    }


    /*
    加载作者统计信息
     */
    private void loadAuthorStatistics(){
        authorStatisticsRepository.deleteAll();

        List<Author> authorList = authorRepository.findAll();
        for(Author author: authorList){
            AuthorStatistics authorStatistics = new AuthorStatistics();
            //复制信息
            authorStatistics.setAuthorId(author.getId());
            authorStatistics.setName(author.getName());
            authorStatistics.setAuthorKeywords(author.getAuthorKeywords());
            authorStatistics.setAffiliation(author.getAffiliation());
            //计算发表数量
            int documentCount = documentRepository.getDocumentsByAuthorId(author.getId()).size();
            authorStatistics.setDocumentCount(documentCount);
            //计算作者专业领域
            String domains = getDomains(author.getAuthorKeywords());
            authorStatistics.setDomains(domains);
            authorStatistics.setActivation(authorRepository.getActivationById(author.getId()));
            authorStatisticsRepository.save(authorStatistics);
        }
    }

    private void updateAuthorStatistics(){
        System.out.println("reading...");
        List<AuthorStatistics> authorStatisticsList = authorStatisticsRepository.findAll();
        int count=0;
        for(AuthorStatistics authorStatistics:authorStatisticsList){
            System.out.println(++count+"/"+authorStatisticsList.size());
            authorStatistics.setActivation(authorRepository.getActivationById(authorStatistics.getAuthorId()));
            authorStatisticsRepository.save(authorStatistics);
        }
        System.out.println("end");
    }

    private String getDomains(String authorKeywords){
        String[] keywordList = authorKeywords.split("[;,]");
        HashMap<String, Integer> keywordMap = new HashMap<>();
        for(String keyword: keywordList){
            if(keyword.length()!=0){
                if(keywordMap.containsKey(keyword)){
                    int count = keywordMap.get(keyword);
                    keywordMap.replace(keyword, count + 1);
                }
                else{
                    keywordMap.put(keyword, 1);
                }
            }
        }
        Set<Map.Entry<String, Integer>> entries = keywordMap.entrySet();
        //將Set集合轉為List集合，為了實用工具類的排序方法
        List<Map.Entry<String, Integer>> list = new ArrayList<>(entries);
        //使用Collections工具類對list進行排序
        list.sort((o1, o2) -> {
            //按照count倒敘排列
            return o2.getValue() - o1.getValue();
        });
        //將list中的數據存入LinkedHashMap中
        String domains = "";
        for(int i=0;i<10 && i<list.size();i++){
            domains += list.get(i).getKey() + "(" + list.get(i).getValue() + ");";
        }
        System.out.println(domains);
        return domains;
    }


    /*
    加载
     */
    private void loadField(){
        /*
        删除表格
         */
        fieldRepository.deleteAll();

        List<List<String>> fieldList = ReadUtil.readCSV(acemap_field_path);
        int count = 0;
        for(List<String> fieldLine:fieldList){
            Field field = new Field();
            field.setId(++count);
            field.setField(fieldLine.get(0));//设置领域名字
            String keywords = fieldLine.get(1).toLowerCase().replaceAll(" ", ",");
            field.setKeywords(keywords);
            System.out.println("Create field: " + field.toString());
            //数据库操作：插入领域
            fieldRepository.save(field);
        }
    }

    private void updateDocument_2(){
        System.out.println("正在取出所有论文...");
        List<Document> documentList = documentRepository.findAll();
        System.out.println("开始处理论文");
        int count = 0;
        for(Document document:documentList){
            count++;
            System.out.println(count+": total " + documentList.size());
            String conferenceInfo = document.getPublicationTitle();
            //设置新的回忆
            if(!conferenceInfo.endsWith(")")){
                document.setPublicationTitle(conferenceInfo + " (ICSE)");
            }
            documentRepository.save(document);
        }
    }
}
