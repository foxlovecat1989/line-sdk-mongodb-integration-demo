package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class VerifyOriginFailedException extends IllegalStateException {
    public static final String ERROR_MSG = "驗證來源失敗";

    public VerifyOriginFailedException() {
        super(ERROR_MSG);
    }

    public VerifyOriginFailedException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
