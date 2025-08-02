package com.kyle.ai.function.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunctionController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel chatModel;

    @GetMapping(value = "function", produces = MediaType.APPLICATION_JSON_VALUE)
    public String function01(@RequestParam("userMessage") String userMessage) {
        return ChatClient.builder(chatModel)
                .build()
                .prompt()
                .system("""
                        你是算数计算器的代理。
                        你能够支持加法运算和乘法运算等操作，其余功能将在后续版本添加，如果用户问的问题不支持请告知详情。
                        在提供加法运算和乘法运算之前，你必须从用户出获取如下信息：两个数字，运算类型。
                        请调用自定义函数执行加法运算和乘法运算。
                        请将中文。
                        """)
                .user(userMessage)
                .functions("addOperation","mulOperation")
                .call()
                .content();
    }
}
