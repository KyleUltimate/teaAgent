package com.kyle.ai.all.config;

import com.kyle.ai.all.function.RecruitServiceFunction;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

@Configuration
public class RagConfiguration {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("你是一名java开发语言专家，对于用户的使用需求做出解答").build();
    }

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();

        String filePath="张三简历.txt";
        TextReader textReader = new TextReader(filePath);
        textReader.getCustomMetadata().put("filePath",filePath);
        List<Document> documents = textReader.get();
        //2 文本切分段落
        TokenTextSplitter splitter =
                new TokenTextSplitter(1200,
                        350, 5,
                        100, true);
        splitter.apply(documents);
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }

    @Bean
    @Description("某某是否有资格面试")
    public Function<RecruitServiceFunction.Request, RecruitServiceFunction.Response> recruitServiceFunction(){
        return new RecruitServiceFunction();
    }
}
