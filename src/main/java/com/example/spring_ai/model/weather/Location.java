package com.example.spring_ai.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record Location(@JsonProperty(required = true, value = "name")
                       @JsonPropertyDescription("name") String name,
                       @JsonProperty(required = true, value = "region")
                       @JsonPropertyDescription("region") String region,
                       @JsonProperty(required = true, value = "country")
                       @JsonPropertyDescription("country") String country) {
}
