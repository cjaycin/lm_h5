package com.frame.dao.jdbc.connection;

import com.frame.comm.SystemConstant;
import com.frame.domain.DataModel;
import com.frame.utils.StringHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 描述：Jdbc数据源配置
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 16:55
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class JdbcConfigure {
    private static final Logger logger = Logger.getLogger(JdbcConfigure.class);
    private static JdbcConfigure jdbcConfigure = new JdbcConfigure();
    private String defaultDatasourceId = "";//系统默认jdbc数据源标志
    private Map<String, DataModel> configureMap = new HashMap<String, DataModel>();//保存jdbc数据源配置项
    private Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();//保存jdbc数据源
    private static final String jdbcConfigureFile = "jdbc-configure.xml";//jdbc配置文件

    /**
     * 私有构造函数
     */
    private JdbcConfigure() {
        loadConfig();
    }

    /**
     * 加载jdbc配置文件
     */
    @SuppressWarnings("unchecked")
    private void loadConfig() {
        InputStream is = null;
        try {
            SAXReader reader = new SAXReader();
            is = ConnManager.class.getResourceAsStream("/" + SystemConstant.CONFIG + File.separator + jdbcConfigureFile);
            Document document = reader.read(is);
            Element root = document.getRootElement(); // 获取根节点
            defaultDatasourceId = root.attributeValue("default", "");//系统默认数据源

            Iterator<Element> items = root.elementIterator("datasource");
            while (items.hasNext()) {
                Element elements = items.next();
                String id = elements.attributeValue("id", "");//数据源标志
                String dbType = elements.attributeValue("dbType", "");//数据库类型
                String description = elements.attributeValue("description", "");//数据库描述

                if (StringHelper.isEmpty(id)) {
                    continue;
                }

                //构建数据源配置项
                DataModel dataModel = new DataModel();
                dataModel.put("id", id);
                dataModel.put("dbType", dbType);
                dataModel.put("description", description);

                Iterator<Element> item = elements.elementIterator("property");
                while (item.hasNext()) {
                    Element element = item.next();
                    dataModel.put(element.attributeValue("name"), element.getText());
                }
                configureMap.put(id, dataModel);

                //构建数据源
                DataSource dataSource = buildDataSource(dataModel);
                dataSourceMap.put(id, dataSource);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 获取单例对象
     *
     * @return 单例对象
     */
    public static JdbcConfigure getInstance() {
        return jdbcConfigure;
    }

    /**
     * 构建数据源
     *
     * @param dr 数据源配置
     * @return 数据源对象
     */
    private DataSource buildDataSource(DataModel dr) {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(dr.getString("driver-name"));
            comboPooledDataSource.setJdbcUrl(dr.getString("url"));
            comboPooledDataSource.setUser(dr.getString("user"));
            comboPooledDataSource.setPassword(dr.getString("password"));

            //如果设为true那么在取得连接的同时将校验连接的有效性
            comboPooledDataSource.setTestConnectionOnCheckin(dr.getBoolean("testConnectionOnCheckin"));
            //如果设为true那么在每个connection提交的时候都将校验其有效性
            comboPooledDataSource.setTestConnectionOnCheckout(dr.getBoolean("testConnectionOnCheckout"));
            //C3P0将建一张指定名称的空表，并使用其自带的查询语句进行测试
            comboPooledDataSource.setAutomaticTestTable(dr.getString("automaticTestTable"));
            //每隔指定秒检查所有连接池中的空闲连接
            comboPooledDataSource.setIdleConnectionTestPeriod(dr.getInt("idleConnectionTestPeriod"));
            //最大空闲时间,指定秒内未使用则连接被丢弃。若为0则永不丢弃
            comboPooledDataSource.setMaxIdleTime(dr.getInt("maxIdleTime"));
            //初始化时获取的链接数，取值应在minPoolSize与maxPoolSize之间
            comboPooledDataSource.setInitialPoolSize(dr.getInt("initialPoolSize"));
            //连接池中保留的最小连接数
            comboPooledDataSource.setMinPoolSize(dr.getInt("minPoolSize"));
            //连接池中保留的最大连接数
            comboPooledDataSource.setMaxPoolSize(dr.getInt("maxPoolSize"));

            return comboPooledDataSource;
        } catch (PropertyVetoException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取缺省数据源
     *
     * @return 默认数据源对象
     */
    public DataSource getDataSource() {
        //如果只有一个数据源，则直接返回该数据源
        if (dataSourceMap.size() == 1) {
            Object[] dataSourceArray = dataSourceMap.values().toArray();
            return (DataSource) dataSourceArray[0];
        }
        if (!StringHelper.isEmpty(defaultDatasourceId)) {
            return getDataSource(defaultDatasourceId);
        }
        return null;
    }

    /**
     * 根据数据源ID获取数据源
     *
     * @param id：数据源ID
     * @return 数据源对象
     */
    public DataSource getDataSource(String id) {
        return dataSourceMap.get(id);
    }


    /**
     * 获取所有数据源配置项
     *
     * @return 所有数据源配置项
     */
    public Map<String, DataModel> getConfigureMapAll() {
        return configureMap;
    }

    /**
     * 获取缺省数据源配置项
     *
     * @return 数据源配置项
     */
    public DataModel getConfigureMap() {
        return configureMap.get(defaultDatasourceId);
    }

    /**
     * 根据数据源ID获取数据源配置项
     *
     * @param id：数据源ID
     * @return 数据源配置项
     */
    public DataModel getConfigureMap(String id) {
        return configureMap.get(id);
    }

    /**
     * 获取缺省的数据源标志
     *
     * @return 数据源标志
     */
    public String getDefaultDatasourceId() {
        return defaultDatasourceId;
    }
}