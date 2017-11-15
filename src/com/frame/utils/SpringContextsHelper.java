package com.frame.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 描述：从Spring上下文中获取Bean
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 22:33
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class SpringContextsHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 获取Object类型的bean
     *
     * @param beanName bean标识
     * @return 对应的对象
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 获取指定类型的bean
     *
     * @param beanName bean标识
     * @param clazs    指定类型
     * @param <T>      指定类型
     * @return对应的对象
     */
    public static <T> T getBean(String beanName, Class<T> clazs) {
        return clazs.cast(getBean(beanName));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextsHelper.applicationContext = applicationContext;
    }
}
