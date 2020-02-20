package com.nju.oasis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

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
}
