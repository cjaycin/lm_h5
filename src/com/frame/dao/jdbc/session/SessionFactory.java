package com.frame.dao.jdbc.session;

import com.frame.dao.jdbc.session.impl.SessionImpl;
import com.frame.utils.StringHelper;

/**
 * 描述：Session工厂
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 17:01
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class SessionFactory {
    /**
     * 获取指定数据源的session会话
     *
     * @param datasourceId 数据源标志
     * @return 数据库会话
     */
    public static ISession getSession(String datasourceId) {
        if (StringHelper.isEmpty(datasourceId)) {
            //返回缺省的数据库会话
            return new SessionImpl();
        } else {
            //返回指定的数据库会话
            return new SessionImpl(datasourceId);
        }

    }
}