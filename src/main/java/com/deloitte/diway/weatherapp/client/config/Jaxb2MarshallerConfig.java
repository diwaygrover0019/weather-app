package com.deloitte.diway.weatherapp.client.config;

import com.deloitte.diway.weatherapp.models.GetCitiesByCountryDataSet;
import com.deloitte.diway.weatherapp.models.GetWeatherResponseDataSet;
import com.deloitte.diway.weatherapp.models.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Jaxb2MarshallerConfig {

    @Value("${global.weather.marshaller.contextPath}")
    private String contextPath;

    @Bean
    @Primary
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this is the package name specified in the <generatePackage> specified in pom.xml in case of jaxb2 based plugin
        // and <sourceRoot> specified in pom.xml in case of apache cxf based plugin
        marshaller.setContextPath(contextPath);
        return marshaller;
    }

    @Bean(name = "citiesByCountryMarshaller")
    public Jaxb2Marshaller citiesByCountryMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetCitiesByCountryDataSet.class, City.class);
        return marshaller;
    }
    
    @Bean(name = "weatherMarshaller")
    public Jaxb2Marshaller weatherMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GetWeatherResponseDataSet.class);
        return marshaller;
    }
}
