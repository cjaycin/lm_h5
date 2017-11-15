package com.frame.web.controller;

import com.frame.business.exception.BusinessException;
import com.frame.comm.WebConstants;
import com.frame.dao.jdbc.common.DBPage;
import com.frame.domain.DataModel;
import com.frame.domain.ExecResult;
import com.frame.utils.ResponseHelper;
import com.frame.utils.SpringContextsHelper;
import com.frame.utils.StringHelper;
import com.frame.web.controller.comm.AbstractController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：通用控制器
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 12:12
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
@Controller
public class CommonController extends AbstractController {
    private static Logger logger = Logger.getLogger(CommonController.class);

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/commonController.do")
    @ResponseBody
    @Override
    public Map<String, Object> execute(HttpServletRequest request) {
        //请求入参
        DataModel reqMap;
        //响应结果
        Map<String, Object> resMap;

        try {
            reqMap = (DataModel) request.getAttribute("reqMap");
            HttpSession session = request.getSession();
            reqMap.put("_session",session);
            if (reqMap.get("requests") != null) {
                //多功能号请求
                List<Map<String, Object>> requests;
                try {
                    requests = (List<Map<String, Object>>) reqMap.get("requests");
                } catch (ClassCastException e) {
                    resMap = ResponseHelper.getResult(-1, "入参requests格式错误");
                    logger.info("响应结果：" + resMap);
                    return resMap;
                }
                if (requests.size() == 0) {
                    resMap = ResponseHelper.getResult(-1, "入参requests值为空");
                } else {
                    //各个功能号的响应结果集
                    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                    resMap = new HashMap<String, Object>();
                    resMap.put("errorNo", "0");
                    resMap.put("errorMsg", "请求成功");
                    resMap.put("results", result);
                    for (Map<String, Object> param : requests) {
                        String func = (String) param.get("func");//请求功能号
                        Map<String, Object> resMapOne;//该功能号对应的响应结果
                        if (StringHelper.isEmpty(func)) {
                            resMapOne = ResponseHelper.getResult(-1, "入参func为空");
                        } else {
                            reqMap.putAll(param);
                            resMapOne = doBusiness(reqMap);
                        }
                        resMapOne.put("func", func);
                        result.add(resMapOne);
                    }
                }
            } else {
                //单功能号请求
                resMap = doBusiness(reqMap);
                String func = reqMap.getString("func");
                if( "100001".equals(func) &&  (Integer)resMap.get("errorNo") == 0){//登录结果写入session中
                    List<DataModel> list = (List<DataModel>)resMap.get("result");
                    DataModel client = (DataModel)list.get(0);
                    if(client != null){
                        session.setAttribute(WebConstants.SESSION_CLIENT_ID, client.getString("user_id"));
                        session.setAttribute(WebConstants.SESSION_CLIENT_LOGIN_ID, client.getString("login_id"));
                        session.setAttribute(WebConstants.SESSION_CLIENT_NAME, client.getString("name"));
                        session.setAttribute(WebConstants.SESSION_CLIENT_TYPE, client.getString("type"));
                        session.setAttribute(WebConstants.SESSION_CLIENT_MOBILENO, client.getString("mobile"));
                        logger.info("用户：【" + client.getString("name")+"】登录");
                    }
                }
                if( "100999".equals(func) &&  (Integer)resMap.get("errorNo") == 0) {//退出，清空session
                    if (session!=null){
                        String name = session.getAttribute(WebConstants.SESSION_CLIENT_NAME)!=null ? session.getAttribute(WebConstants.SESSION_CLIENT_NAME).toString() :"";
                        session.invalidate();
                        logger.info("用户：【"+name+"】退出，清理session完成");
                    }
                }
            }
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            resMap = ResponseHelper.getResult(-1, e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resMap = ResponseHelper.getResult(-1, "系统错误(" + e.getMessage() + ")");
        }
        //logger.info("响应结果：" + resMap);
        request.setAttribute("resMap",resMap);
        return resMap;
    }


    /**
     * 执行功能号
     *
     * @param reqMap 请求入参
     * @return 执行结果(直接返回给浏览器的json)
     */
    private Map<String, Object> doBusiness(DataModel reqMap) {
        //返回结果
        Map<String, Object> result = new HashMap<String, Object>();

        String func = reqMap.getString("func");
        //如果前端传入int类型function，则截取
        if (func.endsWith(".0")) {
            func = func.substring(0, func.indexOf(".0"));
        }
        try {
            //执行指定的功能号
            Method method = ReflectionUtils.findMethod(SpringContextsHelper.getBean("func" + func).getClass(), "execute", new Class[]{DataModel.class});
            ExecResult daoResult = (ExecResult) ReflectionUtils.invokeMethod(method, SpringContextsHelper.getBean("func" + func), reqMap);

            //特殊情况，响应结果直接放在response对象中
            if (daoResult == null) {
                return null;
            }

            //组装结果
            result.put("errorNo", daoResult.getErrorNo());
            result.put("errorMsg", daoResult.getErrorMsg());
            result.put("result", daoResult.getResult() != null ? daoResult.getResult() : new ArrayList<Object>());
            result.put("page", daoResult.getPage() != null ? daoResult.getPage() : new DBPage(0,0));
        } catch (NoSuchBeanDefinitionException e) {
            logger.error(e.getMessage(), e);
            result = ResponseHelper.getResult(-1, "功能号" + func + "暂不支持");
            return result;
        }
        return result;
    }
}
