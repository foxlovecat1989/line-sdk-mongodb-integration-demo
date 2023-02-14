package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class UnknownOriginRequestException extends IllegalAccessException {
    public static final String ERROR_MSG = "未知來源請求 - 拒絕存取";

    public UnknownOriginRequestException() {
        super(ERROR_MSG);
    }

    public UnknownOriginRequestException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}