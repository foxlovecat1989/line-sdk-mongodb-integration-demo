package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class UnknownOriginRequest extends IllegalAccessException {
    public static final String ERROR_MSG = "未知來源請求 - 拒絕存取";

    public UnknownOriginRequest() {
        super(ERROR_MSG);
    }

    public UnknownOriginRequest(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}