package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class JsonFormattedException extends IllegalArgumentException {
    public static final String ERROR_MSG = "JSON格式化時發生錯誤";

    public JsonFormattedException() {
        super(ERROR_MSG);
    }

    public JsonFormattedException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
