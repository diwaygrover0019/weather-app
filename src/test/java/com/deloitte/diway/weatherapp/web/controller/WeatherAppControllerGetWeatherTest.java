package com.deloitte.diway.weatherapp.web.controller;

import com.deloitte.diway.weatherapp.client.GlobalWeatherClient;
import com.deloitte.diway.weatherapp.models.GetWeatherResponseDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.deloitte.diway.weatherapp.mock.transformer.ResponseTransformer.readAllBytes;
import static com.deloitte.diway.weatherapp.utils.Constants.CITY_FIELD_NAME;
import static com.deloitte.diway.weatherapp.utils.Constants.COUNTRY_FIELD_NAME;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WeatherAppControllerGetWeatherTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private GlobalWeatherClient globalWeatherClient;

    @Mock
    private CompletableFuture<String> completableFuture;

    private String URL_GET_WEATHER;
    private String CITY_NAME;
    private String COUNTRY_NAME;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.URL_GET_WEATHER = "/api/getWeather";
        this.CITY_NAME = "Sydney";
        this.COUNTRY_NAME = "Australia";
    }

    @Test
    public void validationErrorCityNotProvided() throws Exception {

        mockMvc.perform(get(URL_GET_WEATHER))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validationErrorCountryNotProvided() throws Exception {

        mockMvc.perform(get(URL_GET_WEATHER)
                .queryParam(CITY_FIELD_NAME, ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validationErrorCityIsBlank() throws Exception {

        mockMvc.perform(get(URL_GET_WEATHER)
                .queryParam(CITY_FIELD_NAME, "")
                .queryParam(COUNTRY_FIELD_NAME, ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validationErrorCountryIsBlank() throws Exception {

        mockMvc.perform(get(URL_GET_WEATHER)
                .queryParam(CITY_FIELD_NAME, CITY_NAME)
                .queryParam(COUNTRY_FIELD_NAME, ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void downstreamThrowsAnyRuntimeException() throws Exception {

        when(globalWeatherClient.callWebService(any(Object.class)))
                .thenThrow(new RuntimeException("RuntimeException occurred during downstream call"));

        mockMvc.perform(get(URL_GET_WEATHER)
                .queryParam(CITY_FIELD_NAME, CITY_NAME)
                .queryParam(COUNTRY_FIELD_NAME, COUNTRY_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void completableFutureThrowsInterruptedException() throws Exception {

        when(completableFuture.get())
                .thenThrow(new InterruptedException("InterruptedException occurred during downstream call"));
        when(globalWeatherClient.callWebService(any(Object.class)))
                .thenReturn(completableFuture);

        mockMvc.perform(get(URL_GET_WEATHER)
                .queryParam(CITY_FIELD_NAME, CITY_NAME)
                .queryParam(COUNTRY_FIELD_NAME, COUNTRY_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void completableFutureThrowsExecutionException() throws Exception {

        when(completableFuture.get())
                .thenThrow(new ExecutionException("ExecutionException occurred during downstream call", new Exception()));
        when(globalWeatherClient.callWebService(any(Object.class)))
                .thenReturn(completableFuture);

        mockMvc.perform(get(URL_GET_WEATHER)
                .queryParam(CITY_FIELD_NAME, CITY_NAME)
                .queryParam(COUNTRY_FIELD_NAME, COUNTRY_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void successGetWeather() throws Exception {

        String futureContent = readAllBytes("src/test/resources/payloads/globalWeather/happy_weather_response.txt");

        when(completableFuture.get())
                .thenReturn(futureContent);
        when(globalWeatherClient.callWebService(any(Object.class)))
                .thenReturn(completableFuture);

        mockMvc.perform(get(URL_GET_WEATHER)
                .queryParam(CITY_FIELD_NAME, CITY_NAME)
                .queryParam(COUNTRY_FIELD_NAME, COUNTRY_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(GetWeatherResponseDataSet.class)))
                .andExpect(status().isOk());

    }

}
