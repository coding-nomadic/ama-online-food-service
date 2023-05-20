package com.order.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderServiceException extends RuntimeException {
    private String message;
    private String errorCode;
    public OrderServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public OrderServiceException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
