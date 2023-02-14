package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class UnknownException extends Exception{
    public static final String ERROR_MSG = "未知錯誤";

    public UnknownException() {
        super(ERROR_MSG);
    }

    public UnknownException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
