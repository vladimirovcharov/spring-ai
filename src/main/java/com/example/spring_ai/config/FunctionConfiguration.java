package com.example.spring_ai.config;

import com.example.spring_ai.functions.WeatherServiceFunction;
import com.example.spring_ai.model.weather.WeatherRequest;
import com.example.spring_ai.model.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@AllArgsConstructor
@Configuration
public class FunctionConfiguration {
    private final WeatherConfigProperties weatherConfigProperties;

    @Bean
    @Description("Get the current weather conditions for the given city.")
    public Function<WeatherRequest, WeatherResponse> currentWeatherFunction() {
        return new WeatherServiceFunction(weatherConfigProperties);
    }
}
