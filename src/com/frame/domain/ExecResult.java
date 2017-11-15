package com.frame.domain;

import com.frame.dao.jdbc.common.DBPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：执行结果
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 19:27
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class ExecResult {
    private int errorNo;//错误代码
    private String errorMsg;//错误提示
    private List<DataModel> result;//查询结果，更新时为空
    private DBPage page;

    public ExecResult() {
    }

    public ExecResult(int errorNo, String errorMsg) {
        this.errorNo = errorNo;
        this.errorMsg = errorMsg;
    }

    public ExecResult(int errorNo, String errorMsg, List<DataModel> result) {
        this.errorNo = errorNo;
        this.errorMsg = errorMsg;
        this.result = result;
    }

    public ExecResult(int errorNo, String errorMsg,DBPage page) {
        this.errorNo = errorNo;
        this.errorMsg = errorMsg;
        this.page = page;
    }

    public ExecResult(int errorNo, String errorMsg, DataModel result) {
        super();
        this.errorNo = errorNo;
        this.errorMsg = errorMsg;
        List<DataModel> list = new ArrayList<DataModel>();
        list.add(result);
        this.result = list;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<DataModel> getResult() {
        return result;
    }

    public void setResult(List<DataModel> result) {
        this.result = result;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public DBPage getPage() {
        return page;
    }

    public void setPage(DBPage page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ExecResult{" + "errorNo=" + errorNo + ", errorMsg='" + errorMsg + '\'' + ", result=" + result + '}';
    }
}
