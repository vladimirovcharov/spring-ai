package com.example.spring_ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "sfg.aiapp")
@Getter
@Setter
public class VectorStoreProperties {
    private String vectorStorePath;
    private List<Resource> documentsToLoad;
}
