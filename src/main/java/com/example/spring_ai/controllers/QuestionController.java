package com.example.spring_ai.controllers;

import com.example.spring_ai.model.*;
import com.example.spring_ai.services.OpenAiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class QuestionController {

    private final OpenAiService openAiService;

    public QuestionController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/capitalWithInfo")
    public GetCapitalWithInfoResponse getCapitalWithInfo(@RequestBody GetCapitalRequest request) {
        return openAiService.getCapitalWithInfo(request);
    }

    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest request) {
        return openAiService.getCapital(request);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAiService.getAnswer(question);
    }

    @PostMapping("/ask-vector")
    public Answer askVectorQuestion(@RequestBody Question question) {
        return openAiService.getVectorAnswer(question);
    }

    @PostMapping("/ask-tow")
    public Answer askTowQuestion(@RequestBody Question question) {
        return openAiService.getTowAnswer(question);
    }

    @PostMapping("/weather")
    public Answer getWeather(@RequestBody Question question) {
        return openAiService.getWeather(question);
    }

    @PostMapping("/weather-json")
    public Answer getWeatherJson(@RequestBody Question question) {
        return openAiService.getWeatherJson(question);
    }

    @PostMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@RequestBody Question question) {
        return openAiService.getImage(question);
    }

    @PostMapping(value = "/vision", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getVision(@Validated @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(openAiService.getDescription(file));
    }

    @PostMapping(value ="/talk", produces = "audio/mpeg")
    public byte[] getTextToVoice(@RequestBody Question question) {
        return openAiService.getSpeech(question);
    }
}
