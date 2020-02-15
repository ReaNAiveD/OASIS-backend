package com.nju.oasis.controller.VO;

import lombok.Builder;
import lombok.Data;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/15
 * @description:
 */
@Data
@Builder
public class ResultVO {

    int result;
    Object data;
    String message;
}
