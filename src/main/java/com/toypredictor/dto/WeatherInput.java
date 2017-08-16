package com.toypredictor.dto;

/**
 * DTO Class for handling Weather features.
 * 
 * Date : 14-Aug-2017
 * 
 * @author Anntinu Josy
 * 
 * @version 1.0
 *
 */
public class WeatherInput {

	/**
	 * Latitude
	 */
	private double latitude;

	/**
	 * Longitude
	 */
	private double longitude;

	/**
	 * Elevation
	 */
	private double elevation;

	/**
	 * Unix Time
	 */
	private String unixTime;

	/**
	 * For output Location
	 */
	private String outputLocation;

	/**
	 * Getter for Latitude
	 * 
	 * @return double
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Setter for Latitude
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Getter for Longitude
	 * 
	 * @return double
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Setter for Longitude
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Getter for Elevation
	 * 
	 * @return double
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * Setter for Elevation
	 * 
	 * @param elevation
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	/**
	 * Getter for TimeStamp
	 * 
	 * @return String
	 */
	public String getUnixTime() {
		return unixTime;
	}

	/**
	 * Setter for TimeStamp
	 * 
	 * @param unixTime
	 */
	public void setUnixTime(String unixTime) {
		this.unixTime = unixTime;
	}

	/**
	 * Getter for Output Location
	 * 
	 * @return
	 */
	public String getOutLocation() {
		return outputLocation;
	}

	/**
	 * Setter for OutputLocation
	 * 
	 * @param outLocation
	 */
	public void setOutLocation(String outLocation) {
		this.outputLocation = outLocation;
	}

}
