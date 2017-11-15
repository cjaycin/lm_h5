package com.frame.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：json工具类
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 12:48
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class JsonHelper {
    private static Gson gson = new Gson();

    /**
     * json转Map
     *
     * @param json json字符串
     * @return Map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        if (StringHelper.isEmpty(json)) {
            return new HashMap<String, Object>();
        }
        return gson.fromJson(json, Map.class);
    }

    /**
     * Map转json
     *
     * @param map map对象
     * @return json字符串
     */
    public static String MapToJson(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        return gson.toJson(map);
    }

    public static Map<String, Object> getJsonMap(String param){
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(StringHelper.isEmpty(param)){
            return null;
        }
        String[] params = param.split("&");
        if (params != null){
            for (int i = 0; i < params.length; i++) {
                String[] keyValues = params[i].split("=");
                retMap.put(keyValues[0], keyValues[1]);
            }
        }
        return retMap;
    }
}
