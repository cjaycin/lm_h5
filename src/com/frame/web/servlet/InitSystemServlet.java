package com.frame.web.servlet;

import com.frame.comm.SystemConstant;
import com.frame.dao.jdbc.connection.ConnManager;
import com.frame.dao.jdbc.connection.JdbcConfigure;
import com.frame.domain.DataModel;
import com.thinkive.market.stock.HQDataBase;
import com.thinkive.market.stock.SSHQUpdateThread;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

/**
 * 描述：系统初始化Servlet
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 10:39
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class InitSystemServlet extends HttpServlet {
    private static final long serialVersionUID = -3955352927643620041L;
    private static Logger logger = Logger.getLogger(InitSystemServlet.class);

    @Override
    public void init() {
        boolean flag = true;

        //初始化log4j
        try {
            initLog4j();
            logger.info("log4j初始化成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            flag = false;
            logger.info("log4j初始化失败");
        }

        //初始jdbc
        /*Map<String, DataModel> configureMap = JdbcConfigure.getInstance().getConfigureMapAll();
        Set<String> keys = configureMap.keySet();
        for (String key : keys) {
            DataModel configure = configureMap.get(key);
            try {
                Connection connection = ConnManager.getConnection(key);
                ConnManager.closeConnection(connection);
                logger.info(configure.get("description") + "jdbc初始化成功");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                flag = false;
                logger.info(configure.get("description") + "jdbc初始化失败");
            }
        }*/

        logger.info("系统初始化完成：" + (flag ? "成功" : "失败") + "!");
    }

    /**
     * 初始化Log4j
     */
    private void initLog4j() {
//        String log4jDir = System.getProperty("user.dir") + File.separator + SystemConstant.LogDir;
//        TimeSizeRollingFileAppender.setLogRootPath(log4jDir);
        String log4jConfigPath = this.getServletContext().getRealPath("") + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + SystemConstant.CONFIG + File.separator + "log4j.xml";
//        DOMConfigurator.configureAndWatch(log4jConfigPath, 9000000);
//        logger = Logger.getLogger(this.getClass());
        DOMConfigurator.configure(log4jConfigPath);
    }
}
