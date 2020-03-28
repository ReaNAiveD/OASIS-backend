package com.nju.oasis.domain.statistics;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Data
@Entity
public class AuthorStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int authorId;
    private String name;
    @Column(name = "author_affiliation")
    private String affiliation;
    private String authorKeywords;

    //文章数
    private int documentCount;
    //擅长领域
    private String domains;
    //活跃度
    private double activation;
}
