package com.example.spring_ai.functions;

import com.example.spring_ai.config.WeatherConfigProperties;
import com.example.spring_ai.model.weather.WeatherRequest;
import com.example.spring_ai.model.weather.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Slf4j
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {
    private final WeatherConfigProperties weatherConfigProperties;
    private final RestClient restClient;

    public WeatherServiceFunction(WeatherConfigProperties weatherConfigProperties) {
        this.weatherConfigProperties = weatherConfigProperties;
        this.restClient = RestClient.create(weatherConfigProperties.getApiUrl());
    }

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        log.info("Weather request: {}", weatherRequest);
        WeatherResponse response = restClient.get()
                .uri("/current.json?key={key}&q={q}", weatherConfigProperties.getApiKey(), weatherRequest.city())
                .retrieve()
                .body(WeatherResponse.class);

        String prettyJson;
        try {
            prettyJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Weather API response:\n{}", prettyJson);
        return response;
    }
}
