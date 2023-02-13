package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class BeanTransformIllegalStateException extends IllegalArgumentException {
    public static final String ERROR_MSG = "Bean轉換型別時發生錯誤";

    public BeanTransformIllegalStateException() {
        super(ERROR_MSG);
    }

    public BeanTransformIllegalStateException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
