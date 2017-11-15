package com.frame.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述：Http工具类
 * 版权：Copyright (c) 2015
 * 时间：2015/2/2 10:42
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class HttpHelper {
    private static Logger logger = Logger.getLogger(HttpHelper.class);

    /**
     * HttpPost方法
     *
     * @param url   请求url
     * @param param XML或JSON格式入参
     * @return 响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) throws Exception {
        Long startTime = System.currentTimeMillis();
        logger.info("-------http post 请求开始-------------------");
        logger.info("请求url：" + url + ",请求入参：" + param);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String responseMsg = null;
        // 设置请求头信息
        post.addHeader("Content-Type", "multipart/form-data;charset=UTF-8");
        try {
            // 请求消息体参数
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(param.getBytes("UTF-8"));

            // 设置请求消息体
            post.setEntity(byteArrayEntity);

            HttpResponse response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                responseMsg = EntityUtils.toString(entity, "UTF-8");
            } else {
                logger.info("请求结果：请求失败，返回错误代码 " + response.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            post.releaseConnection();
            logger.info("请求结果：" + responseMsg);
            long endTime = System.currentTimeMillis();
            logger.info("-------http post 请求结束，耗时："+(endTime - startTime)+"ms-------");
        }
        return responseMsg;
    }

    /**
     * HttpPost方法
     *
     * @param url   请求url
     * @param param key-value格式入参
     * @return 响应结果
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> param) throws Exception {
        Long startTime = System.currentTimeMillis();
        logger.info("-------http post 请求开始-------------------");
        logger.info("请求url：" + url + ",请求入参：" + param);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String responseMsg = null;
        // 设置请求头信息
        post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            Set<String> keys = param.keySet();
            for (String key : keys) {
                nvps.add(new BasicNameValuePair(key, param.get(key)));
            }

            // 设置请求消息体
            post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            HttpResponse response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                responseMsg = EntityUtils.toString(entity, "UTF-8");
            } else {
                logger.info("请求结果：请求失败，返回错误代码 " + response.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            post.releaseConnection();
            logger.info("请求结果：" + responseMsg);
            long endTime = System.currentTimeMillis();
            logger.info("-------http post 请求结束，耗时："+(endTime - startTime)+"ms-------");
        }
        return responseMsg;
    }


    /**
     * HttpGet方法
     *
     * @param url 请求url
     * @return 响应结果
     * @throws Exception
     */
    public static String doGet(String url) throws Exception {
        Long startTime = System.currentTimeMillis();
        logger.info("-------http get 请求开始-------------------");
        logger.info("请求url：" + url);
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String responseMsg = null;
        try {
            HttpResponse response = httpclient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                responseMsg = EntityUtils.toString(entity, "UTF-8");
            } else {
                logger.info("请求结果：请求失败，返回错误代码 " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            get.releaseConnection();
            logger.info("请求结果：" + responseMsg);
            long endTime = System.currentTimeMillis();
            logger.info("-------http get 请求结束，耗时："+(endTime - startTime)+"ms-------");
        }
        return responseMsg;
    }
}
