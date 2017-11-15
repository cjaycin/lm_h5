package com.frame.business.comm;

import com.frame.business.IFunction;
import com.frame.business.exception.BusinessException;
import com.frame.utils.StringHelper;
import org.apache.log4j.Logger;

/**
 * 描述：业务功能基类
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 19:47
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public abstract class AbstractFunction implements IFunction {
    private static final Logger logger = Logger.getLogger(AbstractFunction.class);

    /**
     * 校验入参是否为空
     *
     * @param paramName  参数名
     * @param paramValue 参数值
     */
    protected void checkNullParam(String paramName, String paramValue) {
        if (StringHelper.isEmpty(paramValue)) {
            throw new BusinessException(-1, "入参" + paramName + "为空");
        }
    }

}
