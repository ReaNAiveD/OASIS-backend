package com.nju.oasis.model.map;

import lombok.Builder;
import lombok.Data;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:
 */
@Data
public class Edge {
    private int v1;
    private int v2;
    private Object content;
    private int weight;
}
