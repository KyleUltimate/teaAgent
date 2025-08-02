package com.kyle.ai.chat.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class chatController {

    private final ChatClient chatClient;

    public chatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @GetMapping("chat")
    public String generate(@RequestParam(value = "msg", defaultValue = "hello")
                               String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
