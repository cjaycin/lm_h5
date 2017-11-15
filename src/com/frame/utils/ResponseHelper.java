package com.frame.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：Http响应工具类
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 20:18
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class ResponseHelper {

    /**
     * 构建Http响应
     *
     * @param errorNo  错误码
     * @param errorMsg 响应信息
     * @return 响应结果
     */
    public static Map<String, Object> getResult(int errorNo, String errorMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", new ArrayList<Object>());
        result.put("errorMsg", errorMsg);
        result.put("errorNo", errorNo);
        return result;
    }

    /**
     * 往浏览器输出Json响应结果
     *
     * @param response HttpServletResponse对象
     * @param resMap   响应信息
     */
    public static void outPrint(HttpServletResponse response, Map<String, Object> resMap) {
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(JsonHelper.MapToJson(resMap));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 往浏览器输出String响应结果
     *
     * @param response HttpServletResponse对象
     * @param str      响应信息
     */
    public static void outPrint(HttpServletResponse response, String str) {
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
