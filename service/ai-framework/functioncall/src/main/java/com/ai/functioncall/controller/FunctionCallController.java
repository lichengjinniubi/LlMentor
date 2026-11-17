package com.ai.functioncall.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.functioncall.weather.WeatherRequest;
import com.ai.functioncall.weather.WeatherResponse;
import com.ai.functioncall.weather.WeatherService;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/functioncall")
public class FunctionCallController {

	private final ChatClient chatClient;

	private final FunctionToolCallback<WeatherRequest, WeatherResponse> weatherToolCallback;

	private final WeatherService weatherService;

	public FunctionCallController(DashScopeChatModel chatModel,
			FunctionToolCallback<WeatherRequest, WeatherResponse> weatherToolCallback,
			WeatherService weatherService) {
		this.chatClient = ChatClient.builder(chatModel).build();
		this.weatherToolCallback = weatherToolCallback;
		this.weatherService = weatherService;
	}

	@GetMapping("/weather")
	public String weather(@RequestParam(defaultValue = "北京今天天气怎么样？") String message) {
		return chatClient.prompt()
				.user(message)
				.toolCallbacks(weatherToolCallback)
				.call()
				.content();
	}

	@GetMapping("/weather/stream")
	public Flux<String> weatherStream(@RequestParam(defaultValue = "上海现在适合出门吗？") String message) {
		return chatClient.prompt()
				.user(message)
				.toolCallbacks(weatherToolCallback)
				.stream()
				.content();
	}

	@GetMapping("/tool/weather")
	public WeatherResponse callToolDirectly(@RequestParam(defaultValue = "杭州") String city) {
		return weatherService.getWeather(new WeatherRequest(city));
	}

}
