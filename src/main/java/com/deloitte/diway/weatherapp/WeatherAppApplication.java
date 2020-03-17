package com.deloitte.diway.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

import static com.deloitte.diway.weatherapp.utils.Constants.GLOBAL_WEATHER_THREAD_NAME_PREFIX;
import static com.deloitte.diway.weatherapp.utils.Constants.GLOBAL_WEATHER_THREAD_POOL_SIZE;

@EnableAsync
@SpringBootApplication
public class WeatherAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

	@Bean(name = "taskExecutorGlobalWeatherClient")
	public Executor taskExecutorGlobalWeatherClient() {
		SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
		executor.setConcurrencyLimit(GLOBAL_WEATHER_THREAD_POOL_SIZE);
		executor.setThreadNamePrefix(GLOBAL_WEATHER_THREAD_NAME_PREFIX);
		return executor;
	}
}
