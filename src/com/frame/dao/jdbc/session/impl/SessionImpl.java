package com.frame.dao.jdbc.session.impl;

import com.frame.dao.jdbc.common.DBPage;
import com.frame.dao.jdbc.connection.ConnManager;
import com.frame.dao.jdbc.connection.JdbcConfigure;
import com.frame.dao.jdbc.exception.JdbcException;
import com.frame.dao.jdbc.session.ISession;
import com.frame.domain.DataModel;
import com.thinkive.base.jdbc.DatabaseType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * 描述：数据库会话实现类
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 17:06
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class SessionImpl extends AbstractSession implements ISession {
    private static final Logger logger = Logger.getLogger(SessionImpl.class);

    /**
     * 创建缺省的数据源数据库会话
     */
    public SessionImpl() {
        DataModel configure = JdbcConfigure.getInstance().getConfigureMap();//缺省的数据源配置
        this.conn = ConnManager.getConnection();
        this.datasourceId = configure.getString("id");
        this.dbType = configure.getInt("dbType");
        this.description = configure.getString("description");
    }

    /**
     * 创建指定数据源的数据库会话
     *
     * @param datasourceId 数据源标志
     */
    public SessionImpl(String datasourceId) {
        DataModel configure = JdbcConfigure.getInstance().getConfigureMap(datasourceId);//指定的数据源配置
        this.conn = ConnManager.getConnection(datasourceId);
        this.datasourceId = configure.getString("id");
        this.dbType = configure.getInt("dbType");
        this.description = configure.getString("description");
    }


    /**
     * 在指定的表中增加一条记录
     *
     * @param tableName 表名
     * @param data      数据
     */
    @Override
    public void insert(String tableName, DataModel data) {
        StringBuilder sql1 = new StringBuilder();//预处理sql前面部分(key)
        StringBuilder sql2 = new StringBuilder();//预处理sql后面部分(value)
        Object[] args = null;//预处理值

        sql1.append("insert into ").append(tableName).append("(");
        sql2.append(" values(");

        if (data != null && data.size() > 0) {
            args = new Object[data.size()];
            int i = 0;
            Set<String> keys = data.keySet();
            for (String key : keys) {
                sql1.append(key).append(",");
                sql2.append("?,");
                args[i++] = data.get(key);
            }
            sql1.deleteCharAt(sql1.length() - 1);
            sql2.deleteCharAt(sql2.length() - 1);
        }
        sql1.append(")");
        sql2.append(")");

        sql1.append(sql2);

        update(sql1.toString(), args);
    }

    /**
     * 在指定表中删除满足条件的数据
     *
     * @param tableName 表名
     * @param condition 条件
     */
    @Override
    public void delete(String tableName, DataModel condition) {
        StringBuilder sql = new StringBuilder();//预处理sql
        Object[] args = null;//预处理值

        sql.append("delete from ").append(tableName);
        if (condition != null && condition.size() > 0) {
            args = new Object[condition.size()];
            int i = 0;
            sql.append(" where 1=1");
            Set<String> keys = condition.keySet();
            for (String key : keys) {
                sql.append(" and ").append(key).append("=?");
                args[i++] = condition.get(key);
            }
        }

        update(sql.toString(), args);
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
        StringBuilder sql = new StringBuilder();//预处理sql
        Object[] args = null;//预处理值
        int i = 0;

        sql.append("update ").append(tableName);
        //要更新的值
        if (data != null && data.size() > 0) {
            args = new Object[data.size() + condition.size()];
            sql.append(" set ");
            Set<String> keys = data.keySet();
            for (String key : keys) {
                sql.append(key).append("=?,");
                args[i++] = data.get(key);
            }
            sql.deleteCharAt(sql.length() - 1);
        }
        //更新条件
        if (condition != null && condition.size() > 0) {
            sql.append(" where 1=1");
            Set<String> keys = condition.keySet();
            for (String key : keys) {
                sql.append(" and ").append(key).append("=?");
                args[i++] = condition.get(key);
            }
        }

        update(sql.toString(), args);

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
        StringBuilder sql = new StringBuilder();//预处理sql
        Object[] args = null;//预处理值

        if (fields == null) {
            fields = "*";
        }

        sql.append("select ").append(fields).append(" from ").append(tableName);
        if (condition != null && condition.size() > 0) {
            args = new Object[condition.size()];
            int i = 0;
            sql.append(" where 1=1");
            Set<String> keys = condition.keySet();
            for (String key : keys) {
                sql.append(" and ").append(key).append("=?");
                args[i++] = condition.get(key);
            }
        }

        return queryMap(sql.toString(), args);
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
        StringBuilder sql = new StringBuilder();//预处理sql
        Object[] args = null;//预处理值

        if (fields == null) {
            fields = "*";
        }

        sql.append("select ").append(fields).append(" from ").append(tableName);
        if (condition != null && condition.size() > 0) {
            args = new Object[condition.size()];
            int i = 0;
            sql.append(" where 1=1");
            Set<String> keys = condition.keySet();
            for (String key : keys) {
                sql.append(" and ").append(key).append("=?");
                args[i++] = condition.get(key);
            }
        }

        return queryList(sql.toString(), args);
    }


    /**
     * 执行指定的预处理更新语句（增、删、改）
     *
     * @param sql  预处理更新语句
     * @param args 预处理语句的值
     * @return 影响的行数
     * @throws JdbcException
     */
    @Override
    public int update(String sql, Object[] args) throws JdbcException {
        logger.info("jdbc(" + datasourceId + ")更新语句脚本：[" + sql + "]，入参：" + (args == null ? "[]" : Arrays.asList(args)));
        long beginTime = System.currentTimeMillis();
        PreparedStatement ps = null;
        int result = 0;
        try {
            ps = conn.prepareStatement(sql);
            if (args != null) {
                for (int i = 1; i <= args.length; i++) {
                    ps.setObject(i, args[i - 1]);
                }
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(1008, "执行jdbc(" + datasourceId + ")更新语句失败：" + e.getMessage());
        } finally {
            closeStatement(ps);
        }
        long endTime = System.currentTimeMillis();
        logger.info("jdbc(" + datasourceId + ")更新执行结果：影响" + result + "行，耗时" + (endTime - beginTime) + "ms");
        return result;
    }


    /**
     * 执行指定的预处理查询语句并返回一条记录
     *
     * @param sql  预处理查询语句
     * @param args 预处理语句的值
     * @return 一条记录
     * @throws JdbcException
     */
    @Override
    public DataModel queryMap(String sql, Object[] args) throws JdbcException {
        logger.info("jdbc(" + datasourceId + ")查询语句脚本：[" + sql + "]，入参：" + (args == null ? "[]" : Arrays.asList(args)));
        long beginTime = System.currentTimeMillis();
        DataModel result = new DataModel();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            if (args != null) {
                for (int i = 1; i <= args.length; i++) {
                    ps.setObject(i, args[i - 1]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            //将指针指到需要取出数据的记录行
            if (rs.next()) {
                result = toDataModel(rs, metaData);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(1009, "执行jdbc(" + datasourceId + ")查询语句失败：" + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
        }
        long endTime = System.currentTimeMillis();
        logger.info("jdbc(" + datasourceId + ")查询执行结果：返回" + (result == null ? 0 : 1) + "行，耗时" + (endTime - beginTime) + "ms，结果集为：" + result);
        return result;
    }

    /**
     * 执行指定的预处理查询语句并返回多条记录
     *
     * @param sql  预处理查询语句
     * @param args 预处理语句的值
     * @return 多条记录
     * @throws JdbcException
     */
    @Override
    public List<DataModel> queryList(String sql, Object[] args) throws JdbcException {
        logger.info("jdbc(" + datasourceId + ")查询语句脚本：[" + sql + "]，入参：" + (args == null ? "[]" : Arrays.asList(args)));
        long beginTime = System.currentTimeMillis();
        List<DataModel> result = new ArrayList<DataModel>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            if (args != null) {
                for (int i = 1; i <= args.length; i++) {
                    ps.setObject(i, args[i - 1]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            //将指针指到需要取出数据的记录行
            while (rs.next()) {
                result.add(toDataModel(rs, metaData));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(1009, "执行jdbc(" + datasourceId + ")查询语句失败：" + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
        }
        long endTime = System.currentTimeMillis();
        logger.info("jdbc(" + datasourceId + ")查询执行结果：返回" + result.size() + "行，耗时" + (endTime - beginTime) + "ms，结果集为：" + result);
        return result;
    }

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
    @Override
    public DBPage queryPage(String sql, Object[] args, int curPage, int numPerPage) throws JdbcException {
        String temp = sql;
        int orderIdx = sql.toUpperCase().lastIndexOf(" ORDER ");
        if(orderIdx != -1) {
            temp = sql.substring(0, orderIdx);
        }

        StringBuffer totalSQL = new StringBuffer(" SELECT count(1) FROM ( ");
        totalSQL.append(temp);
        totalSQL.append(" ) totalTable ");
        int totalRows = this.queryInt(totalSQL.toString(), args);
        DBPage page = new DBPage(curPage, numPerPage);
        page.setTotalRows(totalRows);
        int startIndex = page.getStartIndex();
        int endIndex = page.getLastIndex();
        int rows = endIndex - startIndex;
        rows = rows < 0?0:rows;
        List list = this.query(sql, args, startIndex, rows);
        page.setData(list);
        return page;
    }

    public int queryInt(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        int var10;
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long ex = System.currentTimeMillis();
            pstmt = this.conn.prepareStatement(sql);
            if(args != null) {
                for(int time = 1; time <= args.length; ++time) {
                    pstmt.setObject(time, args[time - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long var16 = System.currentTimeMillis() - ex;
            logger.info("执行完成 [time=" + var16 + " millisecond]");
            if(var16 > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + var16 + " millisecond]");
            }

            if(rs.next()) {
                result = rs.getInt(1);
            }

            var10 = result;
        } catch (SQLException var14) {
            throw new JdbcException(1009, "执行jdbc(" + datasourceId + ")查询语句失败：" + var14);
        } finally {
            this.closeResultSet(rs);
            this.closeStatement(pstmt);
        }

        return var10;
    }

    public List query(String sql, Object[] args, int startRow, int rows) {
        StringBuffer sqlBuffer;
        if(this.dbType == DatabaseType.ORACLE) {
            sqlBuffer = new StringBuffer();
            sqlBuffer.append("select * from ( select row_.*, rownum rownum_ from ( ");
            sqlBuffer.append(sql);
            sqlBuffer.append(" ) row_ where rownum <= " + (startRow + rows) + ") where rownum_ > " + startRow + "");
            return this.queryFromSpecialDB(sqlBuffer.toString(), args);
        } else if(this.dbType == DatabaseType.MYSQL) {
            sqlBuffer = new StringBuffer();
            sqlBuffer.append(sql);
            sqlBuffer.append(" limit " + startRow + "," + rows + "");
            return this.queryFromSpecialDB(sqlBuffer.toString(), args);
        } else if(this.dbType == DatabaseType.MSSQL) {
            return this.queryFromOtherDB(sql, args, startRow, rows);
        } else if(this.dbType == DatabaseType.DB2) {
            String sqlBuffer2 = sql.toUpperCase();
            int fromIdx = sqlBuffer2.lastIndexOf(" FROM ");
            int orderIdx = sqlBuffer2.lastIndexOf(" ORDER ");
            String sFrom = "";
            String sOrderBy = "";
            if(orderIdx == -1) {
                sFrom = sql.substring(fromIdx, sql.length());
            } else {
                sFrom = sql.substring(fromIdx, orderIdx);
                sOrderBy = sql.substring(orderIdx);
            }

            String sSelect = sql.substring(0, fromIdx);
            int iEnd = startRow + rows;
            StringBuffer sqlBuffer1 = new StringBuffer();
            sqlBuffer1.append("SELECT * FROM (" + sSelect + ", ROW_NUMBER() OVER(" + sOrderBy + ") AS rn " + sFrom + ") originTable WHERE rn BETWEEN " + startRow + " AND " + iEnd);
            return this.queryFromSpecialDB(sqlBuffer1.toString(), args);
        } else if(this.dbType == DatabaseType.POSTGRESQL) {
            sqlBuffer = new StringBuffer();
            sqlBuffer.append(sql);
            sqlBuffer.append(" limit " + rows + " offset " + (startRow - 1) + "");
            return this.queryFromSpecialDB(sqlBuffer.toString(), args);
        } else {
            return this.queryFromOtherDB(sql, args, startRow, rows);
        }
    }

    private List queryFromSpecialDB(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList list = new ArrayList();

        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long ex = System.currentTimeMillis();
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setFetchSize(50);
            if(args != null) {
                for(int time = 1; time <= args.length; ++time) {
                    pstmt.setObject(time, args[time - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long var17 = System.currentTimeMillis() - ex;
            logger.info("执行完成 [time=" + var17 + " millisecond]");
            if(var17 > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + var17 + " millisecond]");
            }

            ResultSetMetaData metaData = rs.getMetaData();

            while(rs.next()) {
                list.add(this.toDataRow(rs, metaData));
            }

            ArrayList var11 = list;
            return var11;
        } catch (SQLException var15) {
            throw new JdbcException(1009, "执行jdbc(" + datasourceId + ")查询语句失败：" + var15);
        } finally {
            this.closeResultSet(rs);
            this.closeStatement(pstmt);
        }
    }

    private List queryFromOtherDB(String sql, Object[] args, int startRow, int rows) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList list = new ArrayList();

        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long ex = System.currentTimeMillis();
            pstmt = this.conn.prepareStatement(sql, 1005, 1007);
            pstmt.setFetchSize(50);
            if(args != null) {
                for(int time = 1; time <= args.length; ++time) {
                    pstmt.setObject(time, args[time - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long var20 = System.currentTimeMillis() - ex;
            logger.info("执行完成 [time=" + var20 + " millisecond]");
            if(var20 > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + var20 + " millisecond]");
            }

            rs.absolute(startRow);
            ResultSetMetaData metaData = rs.getMetaData();
            int count = 0;

            while(true) {
                if(rs.next()) {
                    ++count;
                    list.add(this.toDataRow(rs, metaData));
                    if(count != rows) {
                        continue;
                    }
                }

                ArrayList var14 = list;
                return var14;
            }
        } catch (SQLException var18) {
            throw new JdbcException(1009, "执行jdbc(" + datasourceId + ")查询语句失败：" + var18);
        } finally {
            this.closeResultSet(rs);
            this.closeStatement(pstmt);
        }
    }

    private DataModel toDataRow(ResultSet rs, ResultSetMetaData metaData) throws SQLException {
        DataModel dataRow = new DataModel();
        int count = metaData.getColumnCount();

        for(int i = 0; i < count; ++i) {
            String fieldName = "";
            if(this.dbType == DatabaseType.MYSQL) {
                fieldName = metaData.getColumnLabel(i + 1);
            } else {
                fieldName = metaData.getColumnName(i + 1);
            }

            Object value = rs.getObject(fieldName);
            if(value instanceof Clob) {
                value = rs.getString(fieldName);
            } else if(value instanceof Blob) {
                value = rs.getBytes(fieldName);
            } else if(value instanceof Date) {
                value = rs.getTimestamp(fieldName);
            }

            dataRow.put(fieldName.toLowerCase(), value);
        }

        return dataRow;
    }
}
