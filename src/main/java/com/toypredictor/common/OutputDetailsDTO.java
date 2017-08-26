package com.toypredictor.common;

import com.toypredictor.common.enums.WeatherCondition;

/**
 * DTO for handling the output weather details.
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common
 * @version 1.0
 */
public class OutputDetailsDTO {

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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the weatherCondition
	 */
	public WeatherCondition getWeatherCondition() {
		return weatherCondition;
	}

	/**
	 * @param weatherCondition
	 *            the weatherCondition to set
	 */
	public void setWeatherCondition(WeatherCondition weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature
	 *            the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the humidity
	 */
	public double getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity
	 *            the humidity to set
	 */
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	/**
	 * @return the pressure
	 */
	public double getPressure() {
		return pressure;
	}

	/**
	 * @param pressure
	 *            the pressure to set
	 */
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the elevation
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * @param elevation
	 *            the elevation to set
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

}
