package com.example.spring_ai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalWithInfoResponse(@JsonPropertyDescription("This is the city name") String capital,
                                         @JsonPropertyDescription("This is an additional info") AdditionalCapitalInfo additionalCapitalInfo) {
}
