package com.example.spring_ai.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAiServiceImplTest {

    @Autowired
    OpenAiService openAiService;

    @Test
    void getAnswer() {
        String answer = openAiService.getAnswer("There are 3 killers in a room. Someone enters the room and kills one of them. How many killers are left in the room? Explain your reasoning step by step.");
        System.out.println("Gor the answer");
        System.out.println(answer);
    }
}