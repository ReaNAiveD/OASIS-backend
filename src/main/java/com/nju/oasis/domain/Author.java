package com.nju.oasis.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/18
 * @description: 作者实体类
 */
@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String firstName;
    private String lastName;
    @Column(name = "author_affiliation")
    private String affiliation;
    private String authorKeywords;
    private String ieeeId;

}