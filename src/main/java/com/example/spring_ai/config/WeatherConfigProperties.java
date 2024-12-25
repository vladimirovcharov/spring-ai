package com.example.spring_ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "weather")
@Configuration
@Getter
@Setter
public class WeatherConfigProperties {
    private String apiKey;
    private String apiUrl;
}
