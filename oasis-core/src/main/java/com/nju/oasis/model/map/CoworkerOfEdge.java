package com.nju.oasis.model.map;

import com.nju.oasis.domain.Document;
import lombok.Data;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:
 */
@Data
public class CoworkerOfEdge {
    public final static String urlHead = "/document/fetch?keywords=";

    private int commonWorks;
    //这是一个查询两人共同作品的url,get方法
    private String worksUrl;
}
