package com.kyle.ai.other.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@RestController
public class ImageModelController {

    @Autowired
    private DashScopeImageModel imageModel;

    @GetMapping("image")
    public void getImage(@RequestParam(value = "msg", defaultValue = "生成一只猪") String msg,
                         HttpServletResponse httpServletResponse) {
        ImageResponse imageResponse = imageModel.call(
                new ImagePrompt(
                        msg,
                        DashScopeImageOptions.builder()
                                .withModel(DashScopeImageApi.DEFAULT_IMAGE_MODEL)
                                .withN(1)
                                .withHeight(1024)
                                .withWidth(1024)
                                .build()
                )
        );

        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        try {
            URL url = URI.create(imageUrl).toURL();
            InputStream in = url.openStream();
            httpServletResponse.setHeader("Content-Type", MediaType.IMAGE_PNG_VALUE);
            httpServletResponse.getOutputStream().write(in.readAllBytes());
            httpServletResponse.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
