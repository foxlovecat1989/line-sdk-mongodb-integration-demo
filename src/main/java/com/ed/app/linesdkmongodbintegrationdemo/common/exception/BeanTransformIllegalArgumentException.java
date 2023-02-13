package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class BeanTransformIllegalArgumentException extends IllegalArgumentException {
    public static final String ERROR_MSG = "Pojo與Po非相對應的型別";

    public BeanTransformIllegalArgumentException() {
        super(ERROR_MSG);
    }

    public BeanTransformIllegalArgumentException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
