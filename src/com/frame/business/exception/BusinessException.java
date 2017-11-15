package com.frame.business.exception;

/**
 * 描述：Business层异常
 * 版权：Copyright (c) 2015
 * 时间：2015/1/11 19:45
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -8635147757923007717L;
    private int errorCode;//错误代码

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
