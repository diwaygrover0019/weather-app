package com.deloitte.diway.weatherapp.error;

import com.deloitte.diway.weatherapp.utils.SystemType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCodes {

    BAD_REQUEST(400, HttpStatus.BAD_REQUEST),
    /*UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED),
    NOT_FOUND(404, HttpStatus.NOT_FOUND),
    CONFLICT(409, HttpStatus.CONFLICT),
    UNPROCESSABLE_ENTITY(422, HttpStatus.UNPROCESSABLE_ENTITY),*/
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR);

    @Getter
    private int code;

    @Getter
    private HttpStatus status;

    @Getter
    private SystemType systemType = SystemType.WEATHER_APP;

    ErrorCodes(int code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    ErrorCodes(int code, HttpStatus status, SystemType systemType) {
        this.code = status.value();
        this.status = status;
        this.systemType = systemType;
    }
}
