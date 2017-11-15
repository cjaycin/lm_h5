package com.frame.dao.jdbc.impl;

import com.frame.dao.jdbc.IJdbcDao;
import com.frame.dao.jdbc.common.DBPage;
import com.frame.dao.jdbc.session.ISession;
import com.frame.domain.DataModel;
import com.thinkive.base.jdbc.session.ResultVO;

import java.util.List;

/**
 * 描述：JdbcDAO实现类
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 17:10
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class JdbcDaoImpl extends AbstractJdbcDao implements IJdbcDao {
    /**
     * 在指定的表中增加一条记录
     *
     * @param tableName 表名
     * @param data      数据
     */
    @Override
    public void insert(String tableName, DataModel data) {
        ISession session = null;
        try {
            session = getSession();
            session.insert(tableName, data);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 在指定表中删除满足条件的数据
     *
     * @param tableName 表名
     * @param condition 条件
     */
    @Override
    public void delete(String tableName, DataModel condition) {
        ISession session = null;
        try {
            session = getSession();
            session.delete(tableName, condition);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 在指定表中修改指定条件的指定数据
     *
     * @param tableName 表名
     * @param data      数据
     * @param condition 条件
     */
    @Override
    public void update(String tableName, DataModel data, DataModel condition) {
        ISession session = null;
        try {
            session = getSession();
            session.update(tableName, data, condition);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }

    }

    /**
     * 在指定表中根据指定条件查询指定字段的一条记录
     *
     * @param tableName 表名
     * @param fields    字段列表（多个字段用“,”隔开，全部字段用“*”表示）
     * @param condition 条件
     * @return 一条记录
     */
    @Override
    public DataModel queryMap(String tableName, String fields, DataModel condition) {
        ISession session = null;
        try {
            session = getSession();
            return session.queryMap(tableName, fields, condition);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 在指定表中根据指定条件查询指定字段的多条记录
     *
     * @param tableName 表名
     * @param fields    字段列表（多个字段用“,”隔开，全部字段用“*”表示）
     * @param condition 条件
     * @return 多条记录
     */
    @Override
    public List<DataModel> queryList(String tableName, String fields, DataModel condition) {
        ISession session = null;
        try {
            session = getSession();
            return session.queryList(tableName, fields, condition);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 执行指定的预处理更新语句（增、删、改）
     *
     * @param sql  预处理语句
     * @param args 预处理语句的值
     * @return 影响的行数
     */
    @Override
    public int update(String sql, Object[] args) {
        ISession session = null;
        try {
            session = getSession();
            return session.update(sql, args);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 执行指定的预处理查询语句并返回一条记录
     *
     * @param sql  预处理查询语句
     * @param args 预处理语句的值
     * @return 一条记录
     */
    @Override
    public DataModel queryMap(String sql, Object[] args) {
        ISession session = null;
        try {
            session = getSession();
            return session.queryMap(sql, args);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 执行指定的预处理查询语句并返回多条记录
     *
     * @param sql  预处理查询语句
     * @param args 预处理语句的值
     * @return 多条记录
     */
    @Override
    public List<DataModel> queryList(String sql, Object[] args) {
        ISession session = null;
        try {
            session = getSession();
            return session.queryList(sql, args);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 分页查询指定预处理语句
     *
     * @param sql        预处理查询语句
     * @param args       预处理语句的值
     * @param curPage    当前页
     * @param numPerPage 每页显示的记录数
     * @retur 分页对象
     */
    @Override
    public DBPage queryPage(String sql, Object[] args, int curPage, int numPerPage) {
        ISession session = null;
        try {
            session = getSession();
            return session.queryPage(sql, args, curPage, numPerPage);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }

    /**
     * 执行存储过程
     *
     * @param procName
     * @param args
     * @return
     */
    public ResultVO executeProc(String procName,Object[] args){
        ISession session = null;
        try {
            session = getSession();
            return session.executeProc(procName, args);
        } finally {
            if (session != null) {
                session.close();
                session = null;
            }
        }
    }
}
