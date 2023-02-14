package com.ed.app.linesdkmongodbintegrationdemo.common.exception.handler;

import com.ed.app.linesdkmongodbintegrationdemo.common.exception.*;
import com.ed.app.linesdkmongodbintegrationdemo.common.model.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandling {

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<HttpResponse> unknownException() {
        return createHttpResponse(INTERNAL_SERVER_ERROR, UnknownException.ERROR_MSG);
    }

    @ExceptionHandler(BeanTransformIllegalArgumentException.class)
    public ResponseEntity<HttpResponse> beanTransformIllegalArgumentException() {
        return createHttpResponse(INTERNAL_SERVER_ERROR, BeanTransformIllegalArgumentException.ERROR_MSG);
    }

    @ExceptionHandler(BeanTransformIllegalStateException.class)
    public ResponseEntity<HttpResponse> beanTransformIllegalStateException() {
        return createHttpResponse(INTERNAL_SERVER_ERROR, BeanTransformIllegalStateException.ERROR_MSG);
    }

    @ExceptionHandler(JsonFormattedException.class)
    public ResponseEntity<HttpResponse> jsonFormattedException() {
        return createHttpResponse(INTERNAL_SERVER_ERROR, JsonFormattedException.ERROR_MSG);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<HttpResponse> dataNotFoundException() {
        return createHttpResponse(INTERNAL_SERVER_ERROR, DataNotFoundException.ERROR_MSG);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        log.error(exception.getMessage());

        return createHttpResponse(INTERNAL_SERVER_ERROR, "發生錯誤");
    }

    protected ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(
                        httpStatus.value(),
                        httpStatus,
                        httpStatus.getReasonPhrase().toUpperCase(),
                        message),
                httpStatus
        );
    }
}
