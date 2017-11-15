package com.frame.web.interceptor;

import com.frame.service.IProgressService;
import com.frame.utils.SpringContextsHelper;
import com.google.gson.JsonSyntaxException;
import com.frame.comm.SystemConfigure;
import com.frame.comm.SystemConstant;
import com.frame.domain.DataModel;
import com.frame.utils.JsonHelper;
import com.frame.utils.ResponseHelper;
import com.frame.utils.StringHelper;
import com.thinkive.base.util.RequestHelper;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.Request;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;

/**
 * 描述：安全拦截器
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 20:10
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class SecurityInterceptor implements HandlerInterceptor {
    private static Logger logger = Logger.getLogger(SecurityInterceptor.class);

    @SuppressWarnings("rawtypes")
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        try {
            //请求入参
            DataModel reqMap = new DataModel();

            if ("GET".equals(httpServletRequest.getMethod())) {
                //GET方法
                Enumeration paramNames = httpServletRequest.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String paramName = (String) paramNames.nextElement();
                    reqMap.put(paramName, httpServletRequest.getParameter(paramName));
                }
                logger.info("请求方法：GET，请求入参：" + reqMap);
            } else if ("POST".equals(httpServletRequest.getMethod())) {
                //POST方法
                String func = httpServletRequest.getParameter("func");

                //请求url里传入功能号：http://219.143.202.8:8080/commonController.do?func=200001
                if (StringHelper.isNotEmpty(func)) {
                    //判断是否是键值对入参
                    String jsonFunctions = SystemConfigure.getInstance().getConfig("functions").getString("jsonFunctions");
                    boolean isJsonFunction = false;
                    if (StringHelper.isNotEmpty(jsonFunctions)) {
                        String[] jsonFunctionArray = jsonFunctions.split(",");
                        for (String jsonFunction : jsonFunctionArray) {
                            if (func.equals(jsonFunction)) {
                                isJsonFunction = true;
                                break;
                            }
                        }
                    }

                    //是json入参
                    if (isJsonFunction) {
                        String reqJson = IOUtils.toString(httpServletRequest.getInputStream(), "UTF-8");
                        Map<String, Object> map = JsonHelper.getJsonMap(reqJson);
                        reqMap.putAll(map);

                    } else {
                        //默认key-value格式入参处理
                        Enumeration paramNames = httpServletRequest.getParameterNames();
                        while (paramNames.hasMoreElements()) {
                            String paramName = (String) paramNames.nextElement();
                            reqMap.put(paramName, URLDecoder.decode(httpServletRequest.getParameter(paramName), "UTF-8"));
                        }
                    }

                } else {
                    //默认key-value格式入参处理
                    Enumeration paramNames = httpServletRequest.getParameterNames();
                    while (paramNames.hasMoreElements()) {
                        String paramName = (String) paramNames.nextElement();
                        reqMap.put(paramName, httpServletRequest.getParameter(paramName));
                    }
                }
                logger.info("请求方法：POST，请求入参：" + reqMap);
            }

            if (reqMap.size() < 1) {
                Map<String, Object> resMap = ResponseHelper.getResult(-1, "入参为空");
                logger.info("响应结果：" + resMap);
                ResponseHelper.outPrint(httpServletResponse, resMap);
                return false;
            }

            //多功能号请求
            if (!StringHelper.isEmpty(reqMap.getString("requests"))) {
                //多功能号仅从redis中校验token（只有登录后且是post请求的功能号才能组装成多功能号请求）
               /* String token = reqMap.getString("token");
                if (StringHelper.isEmpty(token)) {
                    Map<String, Object> resMap = ResponseHelper.getResult(-1, "入参token为空");
                    logger.info("响应结果：" + resMap);
                    ResponseHelper.outPrint(httpServletResponse, resMap);
                    return false;
                }*/
                //从redis中获取token
              /*  IRedisDao redisDao = SpringContextsHelper.getBean("redisDao", IRedisDao.class);
                DataModel user = (DataModel) redisDao.getObj(token);
                logger.info("请求用户信息：user=" + user);
                if (user == null) {
                    Map<String, Object> resMap = ResponseHelper.getResult(1, "token无效或已过期");
                    logger.info("响应结果：" + resMap);
                    ResponseHelper.outPrint(httpServletResponse, resMap);
                    return false;
                } else {
                    reqMap.putAll(user);
                    //重置过期时间
                    redisDao.expire(token, 60 * SystemConfigure.getInstance().getConfig("timeouts").getInt("tokenTimeOut"));
                }*/
                reqMap.put("httpServletResponse", httpServletResponse);
                httpServletRequest.setAttribute("reqMap", reqMap);
                return true;
            }

            //单功能号请求
            String func = reqMap.getString("func");
            if (StringHelper.isEmpty(func)) {
                Map<String, Object> resMap = ResponseHelper.getResult(-1, "入参func为空");
                logger.info("响应结果：" + resMap);
                ResponseHelper.outPrint(httpServletResponse, resMap);
                return false;
            }
            //如果前端传入int类型function，则截取
            if (func.endsWith(".0")) {
                func = func.substring(0, func.indexOf(".0"));
            }

            //判断是否必须使用get方法请求的功能号
            boolean isGetMethodFunctions = true;
            String getMethodFunctions = SystemConfigure.getInstance().getConfig("functions").getString("getMethodFunctions");
            //判断是否必须使用get方法
            if (StringHelper.isNotEmpty(getMethodFunctions)) {
                String unneededCheckArray[] = getMethodFunctions.split(",");
                for (String anUnneededCheckArray : unneededCheckArray) {
                    if (func.equals(anUnneededCheckArray)) {
                        isGetMethodFunctions = false;
                        break;
                    }
                }
            }

            if (isGetMethodFunctions) {
                //post方式
                if ("GET".equals(httpServletRequest.getMethod())) {
                    Map<String, Object> resMap = ResponseHelper.getResult(-1, func + "只能用post方法调用");
                    logger.info("响应结果：" + resMap);
                    ResponseHelper.outPrint(httpServletResponse, resMap);
                    return false;
                }
            } else {
                //get方式
                if ("POST".equals(httpServletRequest.getMethod())) {
                    Map<String, Object> resMap = ResponseHelper.getResult(-1, func + "只能用get方法调用");
                    logger.info("响应结果：" + resMap);
                    ResponseHelper.outPrint(httpServletResponse, resMap);
                    return false;
                }
            }

            //是否需要登录状态校验的功能号
            boolean isNoLimitFunctions = true;
            String noLimitFunctions = SystemConfigure.getInstance().getConfig("functions").getString("noLimitFunctions");
            //判断是否需要登录状态校验
            if (StringHelper.isNotEmpty(noLimitFunctions)) {
                String unneededCheckArray[] = noLimitFunctions.split(",");
                for (String anUnneededCheckArray : unneededCheckArray) {
                    if (func.equals(anUnneededCheckArray)) {
                        isNoLimitFunctions = false;
                        break;
                    }
                }
            }

            //第三方接口，不需要登录状态校验(第三方接口范围：200001-200100)
            if (isNoLimitFunctions) {
                if (Integer.parseInt(func) >= SystemConstant.thirdStartFuncId && Integer.parseInt(func) <= SystemConstant.thirdEndFuncId) {
                    isNoLimitFunctions = false;
                }
            }

            //需要登录状态校验的功能号(不需要判断的延迟到具体功能号里去判断)
            /*if (isNoLimitFunctions) {
                String sessionMobile = (String)httpServletRequest.getSession().getAttribute("mobile");
                if(StringHelper.isEmpty(sessionMobile)){
                    Map<String, Object> resMap = ResponseHelper.getResult(-1, "非法请求");
                    logger.info("响应结果：" + resMap);
                    ResponseHelper.outPrint(httpServletResponse, resMap);
                    return false;
                }
            } else {
                String mobile = reqMap.getString("mobile");
                if(StringHelper.isEmpty(mobile)){
                    Map<String, Object> resMap = ResponseHelper.getResult(-1, "入参mobile为空");
                    logger.info("响应结果：" + resMap);
                    ResponseHelper.outPrint(httpServletResponse, resMap);
                    return false;
                }
                IProgressService progressService = SpringContextsHelper.getBean("progressService", IProgressService.class);
                String userName = progressService.queryCustomerName(mobile);
                if(StringHelper.isEmpty(userName)){
                    Map<String, Object> resMap = ResponseHelper.getResult(-1, "未查询到该手机号相关信息，请确认！");
                    logger.info("响应结果：" + resMap);
                    ResponseHelper.outPrint(httpServletResponse, resMap);
                    return false;
                }
                httpServletRequest.getSession().setAttribute("mobile", mobile);
            }*/

            reqMap.put("httpServletResponse", httpServletResponse);
            reqMap.put("httpServletRequest", httpServletRequest);
            httpServletRequest.setAttribute("reqMap", reqMap);
            return true;
        } catch (JsonSyntaxException e) {
            logger.error(e.getMessage(), e);
            Map<String, Object> resMap = ResponseHelper.getResult(-1, "入参错误，请检查入参格式");
            logger.info("响应结果：" + resMap);
            ResponseHelper.outPrint(httpServletResponse, resMap);
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            Map<String, Object> resMap = ResponseHelper.getResult(-1, "系统错误(" + e.getMessage() + ")");
            logger.info("响应结果：" + resMap);
            ResponseHelper.outPrint(httpServletResponse, resMap);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
    }
}
