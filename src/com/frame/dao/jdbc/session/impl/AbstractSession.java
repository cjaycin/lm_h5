package com.frame.dao.jdbc.session.impl;

import com.frame.dao.jdbc.common.DBType;
import com.frame.dao.jdbc.connection.ConnManager;
import com.frame.dao.jdbc.exception.JdbcException;
import com.frame.dao.jdbc.session.ISession;
import com.frame.domain.DataModel;
import com.thinkive.base.jdbc.session.ResultVO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * 描述：抽象的数据库会话
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 17:04
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public abstract class AbstractSession implements ISession {
    private static final Logger logger = Logger.getLogger(AbstractSession.class);
    protected int dbType;//数据库类型
    protected String datasourceId;//数据源标志
    protected String description;////数据库描述
    protected Connection conn = null;//数据库连接
    private CallableStatement proc = null;
    private ResultSet rs1 = null;
    private ResultSet rs2 = null;

    /**
     * 关闭数据库会话（同时关闭数据库链接）
     */
    @Override
    public void close() {
        ConnManager.closeConnection(conn);
    }

    /**
     * 关闭Statement
     *
     * @param st Statement对象
     */
    protected void closeStatement(Statement st) throws JdbcException {
        try {
            if (st != null) {
                st.close();
                st = null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(1006, "关闭Statement失败");
        }
    }

    /**
     * 关闭ResultSet
     *
     * @param result ResultSet对象
     */
    protected void closeResultSet(ResultSet result) throws JdbcException {
        try {
            if (result != null) {
                result.close();
                result = null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new JdbcException(1007, "关闭ResultSet失败");
        }
    }

    /**
     * 开启数据库事务
     */
    protected void beginTrans() {
        ConnManager.begin(conn);
    }

    /**
     * 提交数据库事务
     */
    protected void commitTrans() {
        ConnManager.commit(conn);
    }

    /**
     * 回滚数据库事务
     */
    protected void rollbackTrans() {
        ConnManager.rollback(conn);
    }


    /**
     * 从ResultSet中取出一条记录转换成DataModel
     *
     * @param rs       ResultSet对象
     * @param metaData DataModel对象
     * @return
     */
    protected DataModel toDataModel(ResultSet rs, ResultSetMetaData metaData) throws SQLException {
        DataModel DataModel = new DataModel();
        int count = metaData.getColumnCount();
        for (int i = 0; i < count; i++) {
            String fieldName = "";
            if (dbType == DBType.MYSQL) {
                fieldName = metaData.getColumnLabel(i + 1);
            } else {
                fieldName = metaData.getColumnName(i + 1);
            }

            Object value = rs.getObject(fieldName);
            if (value instanceof Clob) {
                value = rs.getString(fieldName);
            } else if (value instanceof Blob) {
                value = rs.getBytes(fieldName);
            } else if (value instanceof Date) {
                value = rs.getTimestamp(fieldName);
            }
            //把字段名转换为小写
            DataModel.put(fieldName.toLowerCase(), value);
        }
        return DataModel;
    }

    /**
     * 描述:调用存储过程
     * @param procName 存储过程名称
     * @param args 存储过程输入参数
     */
    public ResultVO executeProc(String procName,Object[] args)
    {
        ArrayList list = new ArrayList();
        ResultVO rsvo = new  ResultVO();
        try
        {
            conn = ConnManager.getConnection(datasourceId);
            String callProcSql = "{ call "+procName+"(?,?";
            if (args != null)
            {
                for (int i = 1; i <= args.length; i++)
                {
                    callProcSql+=",?";
                }
            }
            callProcSql+=") }";
            logger.info("执行存储过程:"+callProcSql);
            proc = conn.prepareCall(callProcSql);
            proc.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            proc.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            if (args != null)
            {
                for (int i = 3; i <= args.length+2; i++)
                {
                    proc.setObject(i, args[i - 3]);
                }
            }
            proc.execute();
            if(proc.getObject(1)!=null)
            {
                rs1 = ((ResultSet) proc.getObject(1));     //输出结果
                ResultSetMetaData metaData=null;
                metaData = rs1.getMetaData();
                while (rs1.next())
                {
                    list.add(toDataModel(rs1, metaData));
                }
            }
            rs2 = (ResultSet) proc.getObject(2);     //输出状态

            rsvo.setResults(list);
            while(rs2.next())
            {
                System.out.println("########################:"+rs2.getString(1)+rs2.getString(2));
                rsvo.setRscode(rs2.getString(1));   //状态码
                rsvo.setRsMsg(rs2.getString(2));    //状态文字描述
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            logger.info("执行存储过程出错："+ex.getMessage());
        }
        finally
        {
            closeResultSet(rs1);
            closeResultSet(rs2);
            closeCSTAMT(proc);
            close();
        }
        return rsvo;
    }

    private void closeCSTAMT(CallableStatement cstmt)
    {
        try
        {
            if (cstmt != null)
            {
                cstmt.close();
                cstmt=null;
            }
        }
        catch (Exception ex)
        {
            logger.error("", ex);
        }
    }
}
