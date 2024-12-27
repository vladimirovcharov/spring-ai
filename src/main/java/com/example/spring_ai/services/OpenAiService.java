package com.example.spring_ai.services;

import com.example.spring_ai.model.Answer;
import com.example.spring_ai.model.GetCapitalRequest;
import com.example.spring_ai.model.GetCapitalResponse;
import com.example.spring_ai.model.GetCapitalWithInfoResponse;
import com.example.spring_ai.model.Question;
import org.springframework.web.multipart.MultipartFile;

public interface OpenAiService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getVectorAnswer(Question question);

    GetCapitalResponse getCapital(GetCapitalRequest request);

    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest request);

    Answer getTowAnswer(Question question);

    Answer getWeather(Question question);

    Answer getWeatherJson(Question question);

    byte[] getImage(Question question);

    String getDescription(MultipartFile file);

    byte[] getSpeech(Question question);
}
