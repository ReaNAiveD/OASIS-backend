package com.nju.oasis.model.map;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:
 */
@Builder
@Data
public class MyMap {
    private List<Vertex> vertices;
    private List<Edge> edges;
}
