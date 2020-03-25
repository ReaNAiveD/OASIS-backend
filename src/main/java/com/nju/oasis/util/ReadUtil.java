package com.nju.oasis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/20
 * @description:
 */
public class ReadUtil {

    public static JSONArray readJsonFile(String path){
        String jsonStr = "";
        try {
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            jsonStr = "[" + sb.toString() + "]";
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray list = JSON.parseArray(jsonStr);
        return list;
    }

    public static List<List<String>> readCSV(String path){
        List<List<String>> resultArray = new ArrayList<>();
        try{
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String columnName = br.readLine();
            System.out.println("Read table: " +columnName);
            while((line=br.readLine())!=null){
                List<String> lineList = Arrays.asList(line.split(","));
                resultArray.add(lineList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultArray;
    }
}
