package com.frame.business;

import com.frame.domain.DataModel;
import com.frame.domain.ExecResult;

/**
 * 描述：业务功能接口
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 19:43
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public interface IFunction {
    /**
     * 接口规范
     *
     * @param dataModel Map入参
     * @return ExecResult结果
     */
    public ExecResult execute(DataModel dataModel);
}
