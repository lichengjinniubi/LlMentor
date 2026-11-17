package com.ai.functioncall.pdd.controller;

import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.functioncall.pdd.model.ChatStatus;
import com.ai.functioncall.pdd.model.OrderChat;
import com.ai.functioncall.pdd.tools.OrderTools;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;

import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/pdd")
public class PddRefundController implements InitializingBean {

	@Autowired
	private DashScopeChatModel chatModel;

	@Autowired
	private ChatMemory chatMemory;

	@Autowired
	private OrderTools orderTools;

	@Value("classpath:prompts/pdd-refund-system-prompt.st")
	private Resource systemText;

	private ChatClient chatClient;

	@GetMapping("/newChat")
	public OrderChat newChat(String userId, String orderId, HttpServletResponse httpServletResponse) {
		httpServletResponse.setCharacterEncoding("UTF-8");

		String chatId = UUID.randomUUID().toString();

		return chatClient.prompt()
				.user(String.format(
						"我要咨询订单相关的售后问题，我的用户id是%s,我的订单号是: %s ,本地的对话Id是 %s，当前状态是 %s",
						userId, orderId, chatId, ChatStatus.CHAT_START.name()))
				.advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId)
						.param("chat_memory_retrieve_size", 100))
				.call()
				.entity(OrderChat.class);
	}

	@GetMapping("/ask")
	public Flux<String> ask(String question, String chatId, HttpServletResponse httpServletResponse) {
		httpServletResponse.setCharacterEncoding("UTF-8");

		return chatClient.prompt()
				.user(question)
				.tools(orderTools)
				.advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId)
						.param("chat_memory_retrieve_size", 100))
				.stream()
				.content();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.chatClient = ChatClient.builder(chatModel)
				.defaultAdvisors(
						new SimpleLoggerAdvisor(),
						MessageChatMemoryAdvisor.builder(chatMemory).build())
				.defaultSystem(systemText)
				.build();
	}

}
