package com.ai.service.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai/prompt")
public class PromptEngineerController implements InitializingBean {

    @Autowired
    private DashScopeChatModel chatModel;

    private ChatClient chatClient;

    @Value("classpath:prompts/open-source-system-prompt.st")
    private Resource systemText;

    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(value = "message") String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        BeanOutputConverter<Book> beanOutputConverter = new BeanOutputConverter<>(Book.class);

        PromptTemplate promptTemplate = new PromptTemplate("""
            请帮我推荐1本java相关的书
            {format}
            """);

        return chatClient.prompt(promptTemplate.create(Map.of("format", beanOutputConverter.getFormat()))).system("你是一个专业的图书推荐人员").stream().content();
    }

    @GetMapping("/promptsEngineer6")
    public Flux<String> chat6(@RequestParam(value = "message") String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        PromptTemplate promptTemplate = new PromptTemplate("请给我推荐几个关于{topic}的开源项目");
        promptTemplate.add("topic", message);

        return chatClient.prompt(promptTemplate.create(Map.of("topic", message))).system("你是一个专业的的github项目收集人员").stream().content();
    }

    @GetMapping("/promptsEngineer7")
    public Flux<String> chat7(@RequestParam(value = "message") String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("language", "Java");
        variables.put("topic", message);
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("请给我推荐几个关于{topic}的开源项目,要求是和编程语言{language}相关的。")
                .variables(variables)
                .build();

        return chatClient.prompt(promptTemplate.create(Map.of("topic", message, "language", "Java")))
                .system("你是一个专业的的github项目收集人员")
                .stream().content();
    }

    @GetMapping("/chat2")
    public Flux<String> chat2(@RequestParam(value = "message") String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        BeanOutputConverter<Book> beanOutputConverter = new BeanOutputConverter<>(Book.class);
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("language", "Java");
        variables.put("topic", message);
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .resource(systemText)
                .variables(variables)
                .build();

        return chatClient.prompt(promptTemplate.create(Map.of("topic", message, "language", "Java","format", beanOutputConverter.getFormat())))
                .system("你是一个专业的的github项目收集人员")
                .stream().content();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.chatClient = ChatClient.builder(chatModel).build();
    }


    public record Book(@JsonPropertyDescription("书籍名称") String title,
                       @JsonPropertyDescription("作者") String author,
                       @JsonPropertyDescription("书籍介绍") String description,
                       @JsonPropertyDescription("价格") BigDecimal price) {

    }

}
