package com.frame.business.implProgress;

import com.frame.business.comm.AbstractFunction;
import com.frame.domain.DataModel;
import com.frame.domain.ExecResult;
import im4crm.IM4CRMService.PROGRESS_INFO;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：获取进度列表（大唐接口）
 * 版权：Copyright (c) 2016
 * 时间：2017/11/11 13:01
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class Func1001 extends AbstractFunction {
    @Override
    public ExecResult execute(DataModel dataModel) {
        ExecResult execResult;
        String acceptNo = dataModel.getString("acceptNo");
        checkNullParam("acceptNo", acceptNo);
        Object sessionObj = dataModel.getSession().getAttribute(acceptNo);
        if (sessionObj == null) {
            execResult = new ExecResult(-1, "会话超时或非法请求！");
            return execResult;
        }
        List list = (ArrayList) dataModel.getSession().getAttribute(acceptNo);
        execResult = new ExecResult(0, "调用成功", list);
        return execResult;
    }
}
