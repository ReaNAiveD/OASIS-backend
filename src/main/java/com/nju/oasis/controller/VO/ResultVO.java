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

    public static int SUCCESS = 0;
    public static int GENERAL_FAILED = 1;
    public static int AFFILIATION_NOT_EXIST = 34;

    int result;
    Object data;
    String message;

    public static ResultVO SUCCESS(Object data){
        return new ResultVO(ResultVO.SUCCESS, data, "");
    }

    public static ResultVO FAILED(int state, Object data, String message){
        return new ResultVO(state, data, message);
    }

    public static ResultVO FAILED(Object data){
        return new ResultVO(ResultVO.GENERAL_FAILED, data, "");
    }

    public static ResultVO FAILED(Object data, String message){
        return new ResultVO(ResultVO.GENERAL_FAILED, data, message);
    }

    public static ResultVO FAILED(int state, String message){
        return new ResultVO(state, null, message);
    }
}
