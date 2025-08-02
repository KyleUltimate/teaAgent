package com.kyle.ai;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class chatDeepseekController {

    @Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("hello")
    public String generate(@RequestParam(value = "message", defaultValue = "hello") String message) {
        String response = this.chatModel.call(message);
        System.out.println("Response: " + response);
        return response;
    }
}
