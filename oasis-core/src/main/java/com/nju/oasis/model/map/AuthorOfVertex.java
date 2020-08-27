package com.nju.oasis.model.map;

import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.Field;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:
 */
@Data
public class AuthorOfVertex {

    private int id;
    private String name;
    private String affiliation;
    //机构Id
    private int affiliationId;
    //论文数
    private int documentCount;
    //作者活跃度
    private double activation;
}
