package com.nju.oasis.domain.statistics;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:对于机构的宏观统计，计算机构的文章数，作者数，引用数，活跃度
 */
@Data
@Entity
public class AffiliationStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int affiliationId;
    private String name;
    private int authorCount;
    private int docCount;
    private int citationCount;
    private double activation;

}
