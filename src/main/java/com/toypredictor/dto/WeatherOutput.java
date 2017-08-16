package com.toypredictor.dto;

import com.toypredictor.enums.WeatherCondition;

/**
 * Class for handling output weather features.
 * 
 * Date : 14-Aug-2017
 * 
 * @author Anntinu Josy
 * 
 * @version 1.0
 *
 */
public class WeatherOutput {

	/**
	 * Represents the location where the weather is predicted
	 */
	private String location;
	/**
	 * Represents the time when the weather record is predicted
	 */
	private String time;

	/**
	 * Represents weather status
	 */
	private WeatherCondition weatherCondition;

	/**
	 * Temperature
	 */
	private double temperature;
	/**
	 * Humidity
	 */
	private double humidity;
	/**
	 * Pressure
	 */
	private double pressure;

	/**
	 * Latitude of location.
	 */
	private double latitude;
	/**
	 * Longitude of location.
	 */
	private double longitude;
	/**
	 * Elevation of location from sea level.
	 */
	private double elevation;

	/**
	 * Getter for Latitude
	 * @return double
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Setter for Latitude
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Getter for Longitude
	 * @return double
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Setter for Longitude
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Getter for Elevation
	 * @return double
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * Setter for Elevation
	 * @param elevation
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	/**
	 * Getter for Location
	 * @return String
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setter for Location
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Getter for Temperature
	 * @return double
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * Setter for Temperature
	 * @param temperature
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Getter for Pressure
	 * @return double
	 */
	public double getPressure() {
		return pressure;
	}

	/**
	 * Setter for Pressure
	 * @param pressure
	 */
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	/**
	 * Getter for Humidity
	 * @return double
	 */
	public double getHumidity() {
		return humidity;
	}

	/**
	 * Setter for Humidity
	 * @param humidity
	 */
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}


	/**
	 * Getter for Weather Condition
	 * @return WeatherCondition
	 */
	public WeatherCondition getWeatherCondition() {
		return weatherCondition;
	}

	/**
	 * Setter for Weather Condition
	 * @param weatherCondition
	 */
	public void setWeatherCondition(WeatherCondition weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	/**
	 * Getter for TimeStamp
	 * @return String
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Setter for TimeStamp
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	public WeatherOutput() {
	}

}
