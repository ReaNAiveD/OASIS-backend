package com.nju.oasis.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.controller.form.EnrichMappingForm;
import com.nju.oasis.domain.Affiliation;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.Field;
import com.nju.oasis.repository.AffiliationRepository;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.repository.FieldRepository;
import com.nju.oasis.util.DbLoader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/7/24
 * @description: 服务，扩充数据库
 */
@Service
public class EnricherService {

    @Autowired
    DbLoader dbLoader;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    AffiliationRepository affiliationRepository;
    @Autowired
    FieldRepository fieldRepository;

    /**
     * 加载一个csv文件到数据库
     * @return
     */
    public ResultVO loadCSVFile(EnrichMappingForm enrichForm, MultipartFile file){
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(',');
        try {
            Reader inputFile = new InputStreamReader(file.getInputStream());
            CSVParser parser = format.parse(inputFile);
            List<CSVRecord> records = parser.getRecords();
            for (CSVRecord record : records){
                //加载一条记录到数据库中
                loadRecord(record, enrichForm);
            }
            return ResultVO.SUCCESS(null);
        }
        catch (IOException e){
            e.printStackTrace();
            return ResultVO.FAILED(null, "文件读取意外中断");
        }
    }

    /**
     * 加载一条记录到数据库中
     */
    private void loadRecord(CSVRecord record, EnrichMappingForm enrichForm){
        /*
        提取论文基本信息
         */
        Document document = new Document();
        document.setTitle(getCell(record, enrichForm.getTitle().getCsvColumn()));
        System.out.println("loadRecord. title: " + document.getTitle());
        document.setPublicationTitle(getCell(record, enrichForm.getPublicationTitle().getCsvColumn()));
        String publicationYearStr = getCell(record, enrichForm.getPublicationYear().getCsvColumn());
        if(StringUtils.isNumeric(publicationYearStr)){
            document.setPublicationYear(Integer.parseInt(publicationYearStr));
        }
        String volumeStr = getCell(record, enrichForm.getVolume().getCsvColumn());
        if(StringUtils.isNumeric(volumeStr)){
            document.setVolume(Integer.parseInt(volumeStr));
        }
        document.setStartPage(getCell(record, enrichForm.getStartPage().getCsvColumn()));
        document.setEndPage(getCell(record, enrichForm.getEndPage().getCsvColumn()));
        document.setDocuAbstract(getCell(record, enrichForm.getAbstractIntro().getCsvColumn()));
        document.setDoi(getCell(record, enrichForm.getDoi().getCsvColumn()));
        document.setPdfLink(getCell(record, enrichForm.getPdfLink().getCsvColumn()));
        document.setKeywords(getCell(record, enrichForm.getKeywords().getCsvColumn()));
        document.setPublisher(getCell(record, enrichForm.getPublisher().getCsvColumn()));
        document.setDocumentIdentifier(getCell(record, enrichForm.getDocumentIdentifier().getCsvColumn()));
        String totalCitationsStr = getCell(record, enrichForm.getTotalCitations().getCsvColumn());
        if(StringUtils.isNumeric(totalCitationsStr)){
            document.setTotalCitations(Integer.parseInt(totalCitationsStr));
        }
        String totalDownloadsStr = getCell(record, enrichForm.getTotalDownload().getCsvColumn());
        if(StringUtils.isNumeric(totalDownloadsStr)){
            document.setTotalDownload(Integer.parseInt(totalDownloadsStr));
        }
        document.setAuthorKeywords(document.getKeywords());

//        document.setTotalCitations(document.getTotalDownload()/50);
        //获取所有的分类信息
        List<Field> fieldList = fieldRepository.findAll();
        List<Double> fieldPossibilityList = dbLoader.getFieldPossibility();
        //为文章设置分类
        dbLoader.setFieldForDocument(fieldList, fieldPossibilityList, document);

        Document savedDocument = documentRepository.save(document);
        //记录生成的id
        int documentId = savedDocument.getId();
        /*
        提取作者和机构
         */
        String authorsStr = getCell(record, enrichForm.getAuthorName().getCsvColumn());
        String[] authorArray = authorsStr.split(enrichForm.getAuthorName().getSplit());
        String affiliationsStr = getCell(record, enrichForm.getAuthorAffiliation().getCsvColumn());
        String[] affiliationArray = affiliationsStr.split(enrichForm.getAuthorAffiliation().getSplit());
        //对于每个作者名字来说
        for(int i=0;i<authorArray.length;i++){
            String authorName = authorArray[i];
            if(authorName.length()==0){
                continue;
            }
            String affiliationName = affiliationArray[i];
            Author author = new Author();
            author.setName(authorName);
            author.setAuthorKeywords(document.getAuthorKeywords());
            author.setAffiliation(affiliationName);
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

            /*
            建立论文-作者关系
             */
            documentRepository.insertDocuAuthorRel(documentId, authorId);
        }

    }

    /**
     * 根据列名获得某一行记录的一个单元格内容
     * @param record
     * @param columnName
     * @return
     */
    private String getCell(CSVRecord record, String columnName){
        try{
            String content = record.get(columnName);
            return content;
        }catch (Exception e){
            return "";
        }
    }

}
