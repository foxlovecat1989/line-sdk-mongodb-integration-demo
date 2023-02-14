package com.ed.app.linesdkmongodbintegrationdemo.common.exception.handler.webhook;

import com.ed.app.linesdkmongodbintegrationdemo.common.exception.CallLineApiException;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.InvalidReplyTokenException;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.UnknownOriginRequestException;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.handler.CommonExceptionHandling;
import com.ed.app.linesdkmongodbintegrationdemo.common.model.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class WebhookExceptionHandling extends CommonExceptionHandling {

    @ExceptionHandler(InvalidKeyException.class)
    public ResponseEntity<HttpResponse> invalidKeyException(InvalidKeyException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UnknownOriginRequestException.class)
    public ResponseEntity<HttpResponse> unknownOriginRequestException(UnknownOriginRequestException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<HttpResponse> noSuchAlgorithmException(NoSuchAlgorithmException exception) {
        return createHttpResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(InvalidReplyTokenException.class)
    public ResponseEntity<HttpResponse> invalidReplyTokenException(InvalidReplyTokenException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CallLineApiException.class)
    public ResponseEntity<HttpResponse> callLineApiException(CallLineApiException exception) {
        return createHttpResponse(BAD_GATEWAY, exception.getMessage());
    }

}
