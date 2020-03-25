package com.nju.oasis.util;

import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/25
 * @description:
 */
public class QuickStart {

    public static void main(String[] args) {
//        test1();
    }

    private static void test1(){
        final String acemap_field_path = "src/main/resources/sourcedata/FieldAcemap.csv";
        List<List<String>> result = ReadUtil.readCSV(acemap_field_path);
        System.out.println(result);
    }
}
