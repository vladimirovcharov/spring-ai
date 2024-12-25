package com.example.spring_ai.services;

import com.example.spring_ai.model.*;
import com.example.spring_ai.model.weather.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OpenAiServiceImpl implements OpenAiService {
    private final ChatModel chatModel;
    private final SimpleVectorStore simpleVectorStore;
    private final VectorStore vectorStore;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info-prompt.st")
    private Resource getCapitalWithInfoPrompt;

    @Value("classpath:templates/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    @Value("classpath:templates/rag-prompt-template-meta.st")
    private Resource ragPromptTemplateMeta;

    @Value("classpath:/templates/system-message.st")
    private Resource systemMessageTemplate;

    public OpenAiServiceImpl(ChatModel chatModel, SimpleVectorStore simpleVectorStore, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.simpleVectorStore = simpleVectorStore;
        this.vectorStore = vectorStore;
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getVectorAnswer(Question question) {
        List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(5));
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplateMeta);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(), "documents", String.join("\n", contentList)));

        contentList.forEach(System.out::println);

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest request) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();
        System.out.println("Format: \n" + format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry(), "format", format));
        ChatResponse response = chatModel.call(prompt);

        String responseContent = response.getResult().getOutput().getContent();
        System.out.println(responseContent);

        return converter.convert(responseContent);
    }

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest request) {
        BeanOutputConverter<GetCapitalWithInfoResponse> converter = new BeanOutputConverter<>(GetCapitalWithInfoResponse.class);
        String format = converter.getFormat();
        System.out.println("Format: \n" + format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry(), "format", format));
        ChatResponse response = chatModel.call(prompt);

        String responseContent = response.getResult().getOutput().getContent();
        System.out.println(responseContent);

        return converter.convert(responseContent);
    }

    @Override
    public Answer getTowAnswer(Question question) {
        PromptTemplate systemMessagePromptTemplate = new SystemPromptTemplate(systemMessageTemplate);
        Message systemMessage = systemMessagePromptTemplate.createMessage();

        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(5));
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Message userMessage = promptTemplate.createMessage(Map.of("input", question.question(), "documents", String.join("\n", contentList)));

        ChatResponse response = chatModel.call(new Prompt(List.of(systemMessage, userMessage)));

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getWeather(Question question) {
        var promptOptions = OpenAiChatOptions.builder().withFunction("currentWeatherFunction").build();

        var userMessage = new PromptTemplate(question.question()).createMessage();

        Message systemMessage = new SystemPromptTemplate("""
                You are a weather service. You receive weather information from a service which gives you
                the information based on the metrics system. You should decide whether it's cold or not.\s""")
                .createMessage();

        var response = chatModel.call(new Prompt(List.of(userMessage, systemMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getWeatherJson(Question question) {
        BeanOutputConverter<WeatherResponse> outputConverter = new BeanOutputConverter<>(WeatherResponse.class);
        var format = outputConverter.getFormat();
        log.info("Format: \n {}", format);

        var promptOptions = OpenAiChatOptions.builder().withFunction("currentWeatherFunction").withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, outputConverter.getJsonSchema())).build();

        var userMessage = new PromptTemplate(question.question()).createMessage();

        var response = chatModel.call(new Prompt(List.of(userMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getContent());
    }
}
