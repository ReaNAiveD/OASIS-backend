package com.nju.oasis.controller.form;

import lombok.Data;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description: 查找信息的对象体
 */
@Data
public class SearchForm {

    /*
    混合搜索关键词，根据此关键词在包括论文标题、作者、年份等中匹配，
    可包含多个关键词，空格分隔，作且运算，可选，空为全部
     */
    private String combined;

    /*
    论文标题搜索关键词，可选，空为全部
     */
    private String title;

    /*
    论文作者搜索关键词，可选，空为全部
     */
    private String author;

    private String myAbstract;

    private String affiliation;

    private String publicationTitle;

    private int yearFrom;

    private int yearTo;

    private String publisher;

    /*
    会议机构，可以是"ase","icse"或"others"
     */
    private String conference;

    /*
    默认为default，可选为early,recent
     */
    private String orderBy;

    /*
    默认为20
     */
    private int pageSize;

    /*
    从0开始
     */
    private int page;
}
