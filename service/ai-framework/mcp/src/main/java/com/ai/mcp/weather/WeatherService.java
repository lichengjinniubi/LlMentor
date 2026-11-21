package com.ai.mcp.weather;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

	@Tool(name = "queryWeatherByCityAndDate", description = "根据城市、日期、区县和街道获取天气信息")
	public WeatherResponse queryWeather(WeatherRequest request) {
		String city = request.getCity();
		String date = request.getDate();
		String district = request.getDistrict();
		String street = request.getStreet();

		double temperature = 10 + Math.random() * 15;

		return new WeatherResponse(
				city,
				date,
				district,
				street,
				"晴朗，有微风",
				Math.round(temperature * 10) / 10.0);
	}

}
