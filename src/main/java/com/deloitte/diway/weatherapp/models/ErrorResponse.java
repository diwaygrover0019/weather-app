package com.deloitte.diway.weatherapp.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ErrorResponse {

    private Integer status;
    private String message;
}
