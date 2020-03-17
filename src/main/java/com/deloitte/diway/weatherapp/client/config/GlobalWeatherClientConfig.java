package com.deloitte.diway.weatherapp.client.config;

import com.deloitte.diway.weatherapp.client.GlobalWeatherClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class GlobalWeatherClientConfig {

    @Value("${global.weather.client.defaultUri}")
    private String defaultUri;

    @Bean
    public GlobalWeatherClient client(Jaxb2Marshaller marshaller) {
        GlobalWeatherClient globalWeatherClient = new GlobalWeatherClient();
        globalWeatherClient.setDefaultUri(defaultUri);
        globalWeatherClient.setMarshaller(marshaller);
        globalWeatherClient.setUnmarshaller(marshaller);
        return globalWeatherClient;
    }
}
