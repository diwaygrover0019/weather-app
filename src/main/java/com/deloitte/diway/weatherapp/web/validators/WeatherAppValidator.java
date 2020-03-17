package com.deloitte.diway.weatherapp.web.validators;

import com.deloitte.diway.weatherapp.error.ErrorCodes;
import com.deloitte.diway.weatherapp.error.exceptions.AppRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.deloitte.diway.weatherapp.utils.Constants.CITY_FIELD_NAME;
import static com.deloitte.diway.weatherapp.utils.Constants.COUNTRY_FIELD_NAME;

@Component
public class WeatherAppValidator {

    private final Logger logger = LoggerFactory.getLogger(WeatherAppValidator.class);

    public void validateCountry(final String country) {
        validateField(COUNTRY_FIELD_NAME, country, ErrorCodes.BAD_REQUEST);
    }

    public void validateCity(final String city) {
        validateField(CITY_FIELD_NAME, city, ErrorCodes.BAD_REQUEST);
    }

    private void validateField(final String fieldName, final String fieldValue, final ErrorCodes errorCode) {
        if(StringUtils.isBlank(fieldValue)) {
            String errorMsg = "Attribute '"+fieldName+"' is required/invalid";
            logger.error(errorMsg);
            throw new AppRuntimeException(errorMsg, errorCode);
        }
    }
}
