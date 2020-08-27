package com.nju.oasis.controller.VO;

import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.Field;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Data
public class AuthorVO {

    /*
    基本信息
     */
    private int id;
    private String name;
    private String firstName;
    private String lastName;
    private String affiliation;
    private String authorKeywords;
    private String ieeeId;
    /*
    合作者信息
     */
    private List<Author> coworkers;
    //论文数
    private int documentCount;
    //所写的论文
    private List<Document> documents;
    /*
    阶段二新增
     */
    //机构Id
    private int affiliationId;
    //作者的领域专业，会按照活跃度降序
    private List<Map<String ,String>> fieldList;
    private double activation;
    private int totalDownloads;
    private int totalCitations;

}
