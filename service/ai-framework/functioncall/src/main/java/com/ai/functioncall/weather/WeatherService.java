package com.ai.functioncall.weather;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {

	private static final Map<String, WeatherResponse> MOCK_WEATHER = Map.of(
			"北京", new WeatherResponse("北京", "晴", 18.0, "白天晴朗，适合户外活动"),
			"上海", new WeatherResponse("上海", "多云", 21.0, "体感舒适，可能有短时阵雨"),
			"广州", new WeatherResponse("广州", "小雨", 26.0, "湿度较大，出门建议带伞"),
			"杭州", new WeatherResponse("杭州", "阴", 20.0, "天色偏阴，气温适宜"));

	public WeatherResponse getWeather(WeatherRequest request) {
		String city = request.city();
		return MOCK_WEATHER.getOrDefault(city,
				new WeatherResponse(city, "未知", 0.0, "暂未收录该城市的实时天气数据"));
	}

}
