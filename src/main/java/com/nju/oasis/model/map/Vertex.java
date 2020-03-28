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
public class Vertex {
    private int id;
    //是否是图的中心
    private boolean isCore;
    private Object content;
    private int category;
}
