package com.kyle.ai.chat.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class chatAiController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("chatai")
    public String chatAi(@RequestParam(value = "msg") String message) {
        return chatClient.prompt().user(message).call().content();
    }

    @GetMapping(value = "chataistream", produces = "text/html;charset=UTF-8")
    public Flux<String> chatAiStream(@RequestParam(value = "msg") String message) {
        return chatClient.prompt().user(message).stream().content();
    }
}
