package com.example.spring_ai.model.weather;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather API request")
public record WeatherRequest(@JsonProperty(required = true, value = "city")
                             @JsonPropertyDescription("The city name, e.g. San Francisco, CA") String city){
}
