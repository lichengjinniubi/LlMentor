package com.ai.functioncall.config;

import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ai.functioncall.weather.WeatherRequest;
import com.ai.functioncall.weather.WeatherResponse;
import com.ai.functioncall.weather.WeatherService;

@Configuration
public class FunctionCallConfig {

	@Bean
	public FunctionToolCallback<WeatherRequest, WeatherResponse> weatherToolCallback(WeatherService weatherService) {
		return FunctionToolCallback.<WeatherRequest, WeatherResponse>builder("getWeather", weatherService::getWeather)
				.description("查询指定城市的实时天气，返回天气状况、温度和出行建议")
				.inputType(WeatherRequest.class)
				.build();
	}

	@Bean
	public ChatMemory chatMemory() {
		return MessageWindowChatMemory.builder().maxMessages(20).build();
	}

}
