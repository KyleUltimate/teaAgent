package com.kyle.ai.chat.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class chatModelController {

    @Autowired
    private ChatModel chatModel;

    @GetMapping("prompt")
    public String prompt(@RequestParam(value = "name") String name,
                         @RequestParam(value = "voice") String voice) {
        String userText = """
                给我推荐上海的至少三种美食
                """;
        UserMessage userMessage = new UserMessage(userText);

        String systemText = """
                你是一个美食咨询助手，可以帮助人们查询美食信息。
                你的名字是{name},
                你应该用你的名字和{voice}的饮食习惯回复用户的请求。
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);

        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        ChatResponse chatResponse = chatModel.call(prompt);
        List<Generation> results = chatResponse.getResults();
        return results.stream().map(x->x.getOutput().getContent()).collect(Collectors.joining(""));
    }

    @GetMapping("chatModel01")
    public String chatModel01(@RequestParam(value = "msg") String msg) {
        return chatModel.call(msg);
    }

    @GetMapping("chatModel02")
    public String chatModel02(@RequestParam(value = "msg") String msg) {
        ChatResponse chatResponse = chatModel.call(
                new Prompt(
                        msg,
                        OpenAiChatOptions.builder()
                                .model("deepseek-chat")
                                .temperature(0.8)
                                .build()
                )
        );
        return chatResponse.getResult().getOutput().getContent();
    }
}
