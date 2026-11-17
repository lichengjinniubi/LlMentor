package com.ai.functioncall.weather;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record WeatherRequest(
		@JsonPropertyDescription("需要查询天气的城市名称，例如北京、上海") String city) {
}
