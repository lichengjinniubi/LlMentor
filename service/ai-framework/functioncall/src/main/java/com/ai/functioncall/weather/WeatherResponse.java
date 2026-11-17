package com.ai.functioncall.weather;

public record WeatherResponse(
		String city,
		String condition,
		double temperatureCelsius,
		String summary) {
}
