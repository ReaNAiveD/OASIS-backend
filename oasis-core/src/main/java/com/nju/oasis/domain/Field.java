package com.nju.oasis.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/22
 * @description:分类类型
 */
@Data
@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String field;
    /*
    该特征是以;划分的关键字。但该属性可能用不到
     */
    private String keywords;
}
