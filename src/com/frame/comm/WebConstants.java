package com.frame.comm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：站点公用常量
 * 版权：Copyright (c) 2016
 * 时间：2016/8/1 16:25
 * 公司：中国联通
 * 作者：李新
 * 版本：1.0
 */
public class WebConstants extends HashMap {
    private static Log log = LogFactory.getLog(WebConstants.class);
    private static Map map;

    /**
     * session中保存的客户登陆IDkey
     */
    public static final String SESSION_CLIENT_LOGIN_ID = "@home_client_loginid";

    /**
     * session中保存的客户自增长id的key
     */
    public static final String SESSION_CLIENT_ID = "@home_client_id";

    /**
     * session中保存的客户的姓名
     */
    public static final String SESSION_CLIENT_NAME = "@home_client_name";

    /**
     * session中保存的客户的类型
     */
    public static final String SESSION_CLIENT_TYPE = "@home_client_type";

    /**
     * session中保存的客户的级别
     */
    public static final String SESSION_CLIENT_LEVEL = "@home_client_level";

    /**
     * session中保存的客户资金账号的key
     */
    public static final String SESSION_CLIENT_ASSETACCOUNT = "@home_client_assetaccount";

    /**
     * session中保存的客户资金账号的密码
     */
    public static final String SESSION_CLIENT_ASSET_PASSWORD = "@trade_client_asset_password";

    /**
     * session中保存的客户网点
     */
    public static final String SESSION_CLIENT_BRANCHNO = "@home_client_branchno";

    /**
     * session中保存的客户手机号码
     */
    public static final String SESSION_CLIENT_MOBILENO = "@home_client_mobileno";

    public WebConstants()
    {
        // initialize only once...
        if (map != null)
            return;

        map = new HashMap();
        Class c = this.getClass();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            int modifier = field.getModifiers();
            //排除private类型
            if (Modifier.isFinal(modifier) && !Modifier.isPrivate(modifier))
            {
                try
                {
                    this.put(field.getName(), field.get(this));
                }
                catch (IllegalAccessException e)
                {
                    //e.printStackTrace();
                    log.error(e);
                }
            }
        }
    }

    public Object get(Object key)
    {
        return map.get(key);
    }


    public Object put(Object key, Object value)
    {
        return map.put(key, value);
    }

}
