package com.example.spring_ai.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record Current(@JsonProperty(required = true, value = "temp_c")
                      @JsonPropertyDescription("temp_c") String temp_c,
                      @JsonProperty(required = true, value = "temp_f")
                      @JsonPropertyDescription("temp_f") String temp_f) {
}
