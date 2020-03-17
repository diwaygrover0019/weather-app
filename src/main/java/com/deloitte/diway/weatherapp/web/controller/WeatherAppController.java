package com.deloitte.diway.weatherapp.web.controller;

import com.deloitte.diway.weatherapp.models.GetCitiesByCountryDataSet;
import com.deloitte.diway.weatherapp.models.GetWeatherResponseDataSet;
import com.deloitte.diway.weatherapp.service.WeatherAppService;
import com.deloitte.diway.weatherapp.web.validators.WeatherAppValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class WeatherAppController {

    private final Logger logger = LoggerFactory.getLogger(WeatherAppController.class);

    @Autowired
    private WeatherAppValidator weatherAppValidator;

    @Autowired
    private WeatherAppService weatherAppService;

    @GetMapping(value = "/getCities", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetCitiesByCountryDataSet> getCitiesByCountry(@RequestParam String country) {
        weatherAppValidator.validateCountry(country);

        logger.info("Get Cities For Country: {}", country);
        return weatherAppService.getCitiesByCountryResponse(country);
    }

    @GetMapping(value = "/getWeather", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GetWeatherResponseDataSet> getWeather(@RequestParam String city, @RequestParam String country) {
        weatherAppValidator.validateCity(city);
        weatherAppValidator.validateCountry(country);

        logger.info("Get Weather For City: {}, Country: {}", city, country);
        return weatherAppService.getWeatherResponse(city, country);
    }
}
