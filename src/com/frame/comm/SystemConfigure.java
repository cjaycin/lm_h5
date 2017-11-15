package com.frame.comm;

import com.frame.domain.DataModel;
import com.frame.utils.StringHelper;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 描述：系统配置
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 11:04
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class SystemConfigure {
    private static Logger logger = Logger.getLogger(SystemConfigure.class);
    private static SystemConfigure systemConfigure = new SystemConfigure();//单例对象
    private Map<String, DataModel> config = new HashMap<String, DataModel>();//保存系统配置项
    private static final String systemConfigureFile = "sys-configure.xml";//系统配置文件名

    /**
     * 私有构造函数
     */
    private SystemConfigure() {
        loadConfig();
    }

    /**
     * 加载系统配置文件
     */
    @SuppressWarnings("rawtypes")
    private void loadConfig() {
        try {
            SAXReader reader = new SAXReader();
            InputStream is = SystemConfigure.class.getResourceAsStream("/" + SystemConstant.CONFIG + File.separator + systemConfigureFile);
            Document document = reader.read(is);
            Element root = document.getRootElement(); // 获取根节点
            Iterator categorys = root.elementIterator("category");
            while (categorys.hasNext()) {
                Element category = (Element) categorys.next();

                DataModel dm = new DataModel();
                config.put(category.attributeValue("name"), dm);

                Iterator items = category.elementIterator("item");
                while (items.hasNext()) {
                    Element item = (Element) items.next();
                    dm.put(item.attributeValue("name"), item.attributeValue("value"));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 获取单例对象
     *
     * @return 单例对象
     */
    public static SystemConfigure getInstance() {
        return systemConfigure;
    }

    /**
     * 获取系统配置
     *
     * @param name：系统配置名称
     * @return 系统配置项
     */
    public DataModel getConfig(String name) {
        if (StringHelper.isEmpty(name)) {
            return null;
        }
        return config.get(name);
    }
}

