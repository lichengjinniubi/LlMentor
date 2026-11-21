package com.ai.mcp.weather;

import org.springframework.ai.tool.annotation.ToolParam;

public class WeatherRequest {

	@ToolParam(description = "城市")
	private String city;

	@ToolParam(description = "日期")
	private String date;

	@ToolParam(description = "区县")
	private String district;

	@ToolParam(description = "街道")
	private String street;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}
