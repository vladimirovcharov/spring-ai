package com.example.spring_ai.controllers;

import com.example.spring_ai.model.*;
import com.example.spring_ai.services.OpenAiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
