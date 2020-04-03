package com.nju.oasis.controller.VO;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/4/4
 * @description:
 */
public class FieldVO{
    private int id;
    private String field;
    /*
    该特征是以;划分的关键字。但该属性可能用不到
     */
    private String keywords;
    private int docCount;
    private int totalActivation;
}
