package com.ai.mcp.weather;

public class WeatherResponse {

	private String city;

	private String date;

	private String district;

	private String street;

	private String condition;

	private double temperatureCelsius;

	public WeatherResponse() {
	}

	public WeatherResponse(String city, String date, String district, String street, String condition,
			double temperatureCelsius) {
		this.city = city;
		this.date = date;
		this.district = district;
		this.street = street;
		this.condition = condition;
		this.temperatureCelsius = temperatureCelsius;
	}

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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getTemperatureCelsius() {
		return temperatureCelsius;
	}

	public void setTemperatureCelsius(double temperatureCelsius) {
		this.temperatureCelsius = temperatureCelsius;
	}

}
