package com.frame.dao.jdbc.exception;

/**
 * 描述：自定义jdbc异常
 * 版权：Copyright (c) 2015
 * 时间：2015/1/25 16:54
 * 公司：中国联通
 * 作者：杨黎明
 * 版本：1.0
 */
public class JdbcException extends RuntimeException {
    private static final long serialVersionUID = -7243617209037778342L;

    private int errorCode;//错误代码

    public JdbcException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public JdbcException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
