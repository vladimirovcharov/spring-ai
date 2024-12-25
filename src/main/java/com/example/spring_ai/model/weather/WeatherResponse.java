package com.example.spring_ai.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record WeatherResponse(@JsonProperty(required = true, value = "location") @JsonPropertyDescription("Location") Location location,
                              @JsonProperty(required = true, value = "current") @JsonPropertyDescription("Current") Current current) {
}
