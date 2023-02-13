package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class DataNotFoundException extends IllegalArgumentException{
    public static final String ERROR_MSG = "查無資料";

    public DataNotFoundException() {
        super(ERROR_MSG);
    }

    public DataNotFoundException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
