package com.ed.app.linesdkmongodbintegrationdemo.common.exception;

public class InvalidReplyTokenException extends IllegalStateException {
    public static final String ERROR_MSG = "已經失效的 ReplyToken";

    public InvalidReplyTokenException() {
        super(ERROR_MSG);
    }

    public InvalidReplyTokenException(String extendErrorMessage) {
        super(ERROR_MSG + " : " + extendErrorMessage);
    }
}
