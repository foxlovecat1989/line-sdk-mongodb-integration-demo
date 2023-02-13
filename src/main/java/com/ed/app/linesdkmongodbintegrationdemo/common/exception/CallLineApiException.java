package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class CallLineApiException extends IllegalStateException {
    public static final String ERROR_MSG = "call Line API發生錯誤";

    public CallLineApiException() {
        super(ERROR_MSG);
    }

    public CallLineApiException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
