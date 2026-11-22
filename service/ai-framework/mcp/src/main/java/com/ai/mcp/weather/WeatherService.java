package com.ai.mcp.weather;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {


	@Tool(name = "getWeather", description = "根据城市名称查询天气信息")
	public String getWeather(WeatherRequest request) {
		String city = request.getCity();
		if (city == null) {
			return "请提供城市名称";
		}
		return switch (city) {
			case "北京" -> "北京: 晴, 25°C";
			case "上海" -> "上海: 多云, 22°C";
			case "深圳" -> "深圳: 小雨, 28°C";
			default -> city + ": 下雪, -20°C";
		};
	}

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



	@Tool(name = "weatherResponmse", description = "根据城市、日期、区县和街道获取天气信息")
	public WeatherResponse weatherResponmse(WeatherRequest request) {
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
