package com.frame.dao.jdbc.connection;

import com.frame.dao.jdbc.exception.JdbcException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 描述：数据库连接管理
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 16:58
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class ConnManager {
    private static final Logger logger = Logger.getLogger(ConnManager.class);
    private static JdbcConfigure jdbcConfigure = JdbcConfigure.getInstance();


    /**
     * 获取缺省数据源Connection链接
     *
     * @return 缺省的数据源链接
     */
    public static Connection getConnection() throws JdbcException {
        Connection conn;
        try {
            DataSource dataSource = jdbcConfigure.getDataSource();
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(-1, "获取jdbc(" + jdbcConfigure.getDefaultDatasourceId() + ")链接失败");
        }
        return conn;
    }

    /**
     * 获取指定id的数据源Connection链接
     *
     * @param id 数据源ID
     * @return 指定id的数据源连接
     * @throws JdbcException
     */
    public static Connection getConnection(String id) throws JdbcException {
        Connection conn;
        try {
            DataSource dataSource = jdbcConfigure.getDataSource(id);
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(-1, "获取(" + id + ")jdbc链接失败");
        }
        return conn;
    }

    /**
     * 关闭Connection链接
     *
     * @param conn 数据库连接
     */
    public static void closeConnection(Connection conn) throws JdbcException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(-1, "关闭jdbc超链接失败");
        }
    }

    /**
     * 开启事务
     *
     * @param conn 数据库连接
     */
    public static void begin(Connection conn) throws JdbcException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(-1, "开启jdbc事务失败");
        }
    }

    /**
     * 提交事务
     *
     * @param conn 数据库连接
     */
    public static void commit(Connection conn) throws JdbcException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.commit();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(-1, "提交jdbc事务失败");
        }
    }

    /**
     * 回滚事务
     *
     * @param conn 数据库连接
     */
    public static void rollback(Connection conn) throws JdbcException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(-1, "回滚jdbc事务失败");
        }
    }
}
