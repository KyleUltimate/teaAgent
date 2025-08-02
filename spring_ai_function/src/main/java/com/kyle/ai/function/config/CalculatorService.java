package com.kyle.ai.function.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class CalculatorService {
    public record AddOperation(int a, int b) {

    }

    public record MulOperation(int m, int n) {

    }

    @Bean
    @Description("加法运算")
    public Function<AddOperation,Integer> addOperation() {
        return request -> request.a + request.b;
    }

    @Bean
    @Description("乘法运算")
    public Function<MulOperation,Integer> mulOperation() {
        return request -> request.m * request.n;
    }
}
