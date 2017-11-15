package com.frame.service.impl;

import com.frame.comm.SystemConfigure;
import com.frame.domain.DataModel;
import com.frame.service.IProgressService;
import com.frame.utils.XmlHelper;
import com.frame.utils.XmlUtils;
import im4crm.IM4CRMService.*;
import net.sf.json.JSONObject;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：进度相关服务实现类
 * 版权：Copyright (c) 2016
 * 时间：2016/8/1 13:58
 * 公司：中国联通
 * 版本：1.0
 */
public class ProgressServiceImpl implements IProgressService {
    private static final Logger logger = Logger.getLogger(ProgressServiceImpl.class);

    /**
     * 查询接口获取业务进度列表
     *
     * @param req
     * @return
     */
    public RSP_BODY queryRemote(BROADBAND_SELF_QUERY_REQ req) {
        RSP_BODY rsp_body = null;
        String ossURL = SystemConfigure.getInstance().getConfig("remote").getString("wsdl_url");
        String requestXml = "<UNI_BSS_BODY> \n" +
                "\t<broadbandSelfQueryReq:BROADBAND_SELF_QUERY_REQ\n" +
                "xsi:schemaLocation=\"http://ws.chinaunicom.cn/FixedOrderSer/unibssBody/broadbandSelfQueryReq broadbandSelfQueryReq.xsd\"\n" +
                "xmlns:broadbandSelfQueryReq=\"http://ws.chinaunicom.cn/FixedOrderSer/unibssBody/broadbandSelfQueryReq\" \n" +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> \n" +
                "\t<broadbandSelfQueryReq:SERVICE_TYPE>" + req.getServicetype() + "</broadbandSelfQueryReq:SERVICE_TYPE>\n" +
                "\t<broadbandSelfQueryReq:QUE_TYPE>" + req.getQuetype() + "</broadbandSelfQueryReq:QUE_TYPE>\n" +
                "\t<broadbandSelfQueryReq:QUE_TYPE_INFO>" + req.getQuetypeinfo() + "</broadbandSelfQueryReq:QUE_TYPE_INFO>\n" +
                "\t</broadbandSelfQueryReq:BROADBAND_SELF_QUERY_REQ>\n" +
                "</UNI_BSS_BODY>";
        String responseXml = null;
        try {
            IM4CRMServiceSoapBindingStub stub = new IM4CRMServiceSoapBindingStub(new URL(ossURL), new Service());
            System.out.println("requestXml:" + requestXml);
            responseXml = stub.svcCallIOMByCRM("orderprogressquery", null, requestXml);
            System.out.println("responseXml:" + responseXml);
            rsp_body = (RSP_BODY) XmlHelper.xmlToBean(responseXml, RSP_BODY.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsp_body;
    }

}
