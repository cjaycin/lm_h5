package com.frame.dao.jdbc.session;

import com.frame.dao.jdbc.common.DBPage;
import com.frame.dao.jdbc.exception.JdbcException;
import com.frame.domain.DataModel;
import com.thinkive.base.jdbc.session.ResultVO;

import java.util.List;

/**
 * 描述：数据库会话接口
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 17:02
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public interface ISession {
    /**
     * 在指定的表中增加一条记录
     *
     * @param tableName 表名
     * @param data      数据
     */
    public void insert(String tableName, DataModel data);

    /**
     * 在指定表中删除满足条件的数据
     *
     * @param tableName 表名
     * @param condition 条件
     */
    public void delete(String tableName, DataModel condition);


    /**
     * 在指定表中修改指定条件的指定数据
     *
     * @param tableName 表名
     * @param data      数据
     * @param condition 条件
     */
    public void update(String tableName, DataModel data, DataModel condition);


    /**
     * 在指定表中根据指定条件查询指定字段的一条记录
     *
     * @param tableName 表名
     * @param fields    字段列表（多个字段用“,”隔开，全部字段用“*”表示）
     * @param condition 条件
     * @return 一条记录
     */
    public DataModel queryMap(String tableName, String fields, DataModel condition);


    /**
     * 在指定表中根据指定条件查询指定字段的多条记录
     *
     * @param tableName 表名
     * @param fields    字段列表（多个字段用“,”隔开，全部字段用“*”表示）
     * @param condition 条件
     * @return 多条记录
     */
    public List<DataModel> queryList(String tableName, String fields, DataModel condition);


    /**
     * 执行指定的预处理更新语句（增、删、改）
     *
     * @param sql  预处理语句
     * @param args 预处理语句的值
     * @return 影响的行数
     * @throws JdbcException
     */
    public int update(String sql, Object[] args) throws JdbcException;

    /**
     * 执行指定的预处理查询语句并返回一条记录
     *
     * @param sql  预处理查询语句
     * @param args 预处理语句的值
     * @return 一条记录
     * @throws JdbcException
     */
    public DataModel queryMap(String sql, Object[] args) throws JdbcException;

    /**
     * 执行指定的预处理查询语句并返回多条记录
     *
     * @param sql  预处理查询语句
     * @param args 预处理语句的值
     * @return 多条记录
     * @throws JdbcException
     */
    public List<DataModel> queryList(String sql, Object[] args) throws JdbcException;

    /**
     * 分页查询指定预处理语句
     *
     * @param sql        预处理查询语句
     * @param args       预处理语句的值
     * @param curPage    当前页
     * @param numPerPage 每页显示的记录数
     * @return 分页对象
     * @throws JdbcException
     */
    public DBPage queryPage(String sql, Object[] args, int curPage, int numPerPage) throws JdbcException;

    /**
     * 执行存储过程
     *
     * @param procName
     * @param args
     * @return
     */
    public ResultVO executeProc(String procName, Object[] args);

    /**
     * 关闭数据库会话（同时关闭数据库链接）
     */
    public void close();
}
