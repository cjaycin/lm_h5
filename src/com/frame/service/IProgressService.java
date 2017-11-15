package com.frame.service;

import im4crm.IM4CRMService.*;

import java.util.List;

/**
 * 描述：进度相关服务接口
 * 版权：Copyright (c) 2016
 * 时间：2016/8/1 13:58
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public interface IProgressService {
    /**
     * 查询接口获取业务进度列表
     *
     * @param req
     * @return
     */
    public RSP_BODY queryRemote(BROADBAND_SELF_QUERY_REQ req);
}
