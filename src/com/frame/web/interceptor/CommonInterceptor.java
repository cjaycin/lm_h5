package com.frame.web.interceptor;

import com.frame.comm.SystemConfigure;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述：通用拦截器
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 12:03
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class CommonInterceptor implements HandlerInterceptor {
    private static Logger logger = Logger.getLogger(CommonInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        logger.info("请求开始");

        // 存放请求开始时间
        httpServletRequest.setAttribute("startTime", System.currentTimeMillis());
        // 解决ajax跨域
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 解决响应中文乱码
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html;charset=UTF-8");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        long startTime = Long.valueOf(String.valueOf(httpServletRequest.getAttribute("startTime")));
        long endTime = System.currentTimeMillis();

        //请求超时警告
        if ((endTime - startTime) > SystemConfigure.getInstance().getConfig("timeouts").getInt("funcwarnTime") * 60000) {
            logger.warn("请求结束, 耗时" + (endTime - startTime) + "ms");
        }
        logger.info("请求结束,响应结果：" + httpServletRequest.getAttribute("resMap") + ", 耗时" + (endTime - startTime) + "ms");
    }
}
