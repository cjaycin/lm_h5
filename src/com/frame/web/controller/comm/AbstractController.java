package com.frame.web.controller.comm;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：基础控制器
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 12:10
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public abstract class AbstractController {
    /**
     * 获取返回结果
     *
     * @param errorNo  错误代码
     * @param errorMsg 错误信息
     * @return 返回结果
     */
    protected Map<String, Object> getResult(int errorNo, String errorMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", new ArrayList<Object>());
        result.put("errorMsg", errorMsg);
        result.put("errorNo", errorNo);
        return result;
    }

    /**
     * 通用接口
     *
     * @param request HttpRequest请求
     * @return Map对象
     */
    public abstract Map<String, Object> execute(HttpServletRequest request);
}
