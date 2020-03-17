package com.deloitte.diway.weatherapp.service;

import com.deloitte.diway.weatherapp.client.GlobalWeatherClient;
import com.deloitte.diway.weatherapp.error.ErrorCodes;
import com.deloitte.diway.weatherapp.error.exceptions.AppRuntimeException;
import com.deloitte.diway.weatherapp.models.GetCitiesByCountryDataSet;
import com.deloitte.diway.weatherapp.models.GetWeatherResponseDataSet;
import net.webservicex.GetCitiesByCountry;
import net.webservicex.GetWeather;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.xml.transform.StringSource;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherAppService {

    private final Logger logger = LoggerFactory.getLogger(WeatherAppService.class);

    private static final String START_CDATA = "<![CDATA[";
    private static final String REGEX = ">\\s*<";
    private static final String REPLACEMENT = "><";

    @Autowired
    private GlobalWeatherClient globalWeatherClient;

    @Autowired
    @Qualifier("citiesByCountryMarshaller")
    private Jaxb2Marshaller citiesByCountryMarshaller;

    @Autowired
    @Qualifier("weatherMarshaller")
    private Jaxb2Marshaller weatherMarshaller;

    public Mono<GetCitiesByCountryDataSet> getCitiesByCountryResponse(final String country) {
        try {
            GetCitiesByCountry getCitiesByCountry = new GetCitiesByCountry();
            getCitiesByCountry.setCountryName(country);

            CompletableFuture<String> futureResponse = globalWeatherClient.callWebService(getCitiesByCountry);
            String response = modifyResponse(futureResponse.get());

            GetCitiesByCountryDataSet dataSet = (GetCitiesByCountryDataSet) citiesByCountryMarshaller.unmarshal(new StringSource(response));
            logger.info("getCitiesByCountryResponse: {}", dataSet);

            return Mono.just(dataSet);
        } catch (Exception ex) {
            String errorMsg = "getCitiesByCountry: exception while fetching downstream response";
            logger.error(errorMsg + ", cause: {}, message: {}, class: {}", ex.getCause(), ex.getMessage(), ex.getClass());
            throw new AppRuntimeException(errorMsg, ErrorCodes.INTERNAL_SERVER_ERROR, ex);
        }
    }


    public Mono<GetWeatherResponseDataSet> getWeatherResponse(final String city, final String country) {
        try {
            GetWeather getWeather = new GetWeather();
            getWeather.setCityName(city);
            getWeather.setCountryName(country);

            CompletableFuture<String> futureResponse = globalWeatherClient.callWebService(getWeather);
            String response = modifyResponse(futureResponse.get());

            GetWeatherResponseDataSet dataSet = (GetWeatherResponseDataSet) weatherMarshaller.unmarshal(new StringSource(response));
            logger.info("getWeatherResponse: {}", dataSet);

            return Mono.just(dataSet);
        } catch (Exception ex) {
            String errorMsg = "getWeather: Exception while fetching downstream response";
            logger.error(errorMsg + ", cause: {}, message: {}, class: {}", ex.getCause(), ex.getMessage(), ex.getClass());
            throw new AppRuntimeException(errorMsg, ErrorCodes.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private String modifyResponse(String response) {
        if (response.startsWith(START_CDATA)) {
            response = response.substring(START_CDATA.length(), response.length() - 4);
        }
        response = StringEscapeUtils.unescapeXml(response);
        response = response.replaceAll(REGEX, REPLACEMENT);
        return response;
    }
}
