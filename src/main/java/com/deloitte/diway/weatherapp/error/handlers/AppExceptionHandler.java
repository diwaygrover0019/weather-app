package com.deloitte.diway.weatherapp.error.handlers;

import com.deloitte.diway.weatherapp.error.exceptions.AppRuntimeException;
import com.deloitte.diway.weatherapp.models.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppRuntimeException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> appRuntimeException(WebRequest request, AppRuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .setMessage(ex.getMessage())
                .setStatus(ex.getErrorCodes().getCode());

        return new ResponseEntity(errorResponse, ex.getErrorCodes().getStatus());
    }

    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse()
                .setMessage(ex.getMessage())
                .setStatus(status.value());
        return new ResponseEntity(errorResponse, status);
    }
}
