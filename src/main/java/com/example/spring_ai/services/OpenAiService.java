package com.example.spring_ai.services;

import com.example.spring_ai.model.Answer;
import com.example.spring_ai.model.Question;

public interface OpenAiService {

    String getAnswer(String question);

    Answer getAnswer(Question question);
}