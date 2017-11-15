package com.frame.business.implProgress;

import com.frame.business.comm.AbstractFunction;
import com.frame.domain.DataModel;
import com.frame.domain.ExecResult;
import com.frame.service.IProgressService;
import com.frame.utils.StringHelper;
import im4crm.IM4CRMService.*;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 描述：
 * 版权：Copyright (c) 2016
 * 时间：2016/8/11 13:01
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class Func100 extends AbstractFunction {
    private IProgressService progressService;

    public void setProgressService(IProgressService progressService) {
        this.progressService = progressService;
    }

    private static final Logger logger = Logger.getLogger(Func100.class);

    @Override
    public ExecResult execute(DataModel dataModel) {
        ExecResult execResult;
        String mobile = dataModel.getString("mobile");
        checkNullParam("mobile", mobile);
//        dataModel.getSession().setAttribute("mobile", mobile);

//        String mobile = (String)dataModel.getSession().getAttribute("mobile");
        if (StringHelper.isEmpty(mobile)) {
            execResult = new ExecResult(-1, "非法请求！", new DataModel());
            return execResult;
        }
        BROADBAND_SELF_QUERY_REQ req = new BROADBAND_SELF_QUERY_REQ();
        req.setServicetype("1");
        req.setQuetype("1");
        req.setQuetypeinfo(mobile);

        RSP_BODY body = progressService.queryRemote(req);

        BROADBAND_SELF_QUERY_RSP rsp = body.getBroadbandSelfQueryRsp();
        String respCode = rsp.getRespCode();

        if ("0000".equals(respCode)) {
            //成功
            DataModel data = new DataModel();
            List<ORDER_INFO> orderInfoList = rsp.getOrderInfo();
            Iterator<ORDER_INFO> iterator = orderInfoList.iterator();
            while (iterator.hasNext()) {
                ORDER_INFO order_info = iterator.next();
                dataModel.getSession().setAttribute(order_info.getAcceptNo(), order_info.getProgressInfo());    //进入信息存于session
            }
            data.put("data", orderInfoList);
            execResult = new ExecResult(0, "调用远程接口成功！", data);
        } else if ("1328".equals(respCode)) {
            //查询无记录
            DataModel data = new DataModel();
            data.put("data", new ArrayList());
            execResult = new ExecResult(0, "调用远程接口成功！", data);
        } else {
            //失败
            execResult = new ExecResult(-1, "调用远程接口出现异常！", new DataModel());
        }
        return execResult;
    }
}
