package com.frame.dao.jdbc.impl;

import com.frame.dao.jdbc.IJdbcDao;
import com.frame.dao.jdbc.session.ISession;
import com.frame.dao.jdbc.session.SessionFactory;

/**
 * 描述：抽象JdbcDAO
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 17:09
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public abstract class AbstractJdbcDao implements IJdbcDao{
    public String datasourceId;//数据源标志
    /**
     * 获取Session
     *
     * @return 数据库会话
     */
    protected ISession getSession() {
        return SessionFactory.getSession(datasourceId);
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }
}
