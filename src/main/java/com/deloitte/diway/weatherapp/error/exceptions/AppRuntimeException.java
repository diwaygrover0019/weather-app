package com.deloitte.diway.weatherapp.error.exceptions;

import com.deloitte.diway.weatherapp.error.ErrorCodes;
import lombok.Getter;

public class AppRuntimeException extends RuntimeException {

    @Getter
    private ErrorCodes errorCodes = ErrorCodes.INTERNAL_SERVER_ERROR;

    @Getter
    private Throwable exception;

    public AppRuntimeException(String errorMsg, ErrorCodes errorCodes) {
        super(errorMsg);
        this.errorCodes = errorCodes;
    }

    public AppRuntimeException(String errorMsg, ErrorCodes errorCodes, Throwable ex) {
        super(errorMsg, ex);
        this.errorCodes = errorCodes;
        this.exception = ex;
    }
}
