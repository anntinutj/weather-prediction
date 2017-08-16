package com.toypredictor.exceptions;

import org.apache.log4j.Logger;

/**
 * Custom exception handler for weather-prediction Project
 * 
 * Date : 14-Aug-2017
 * 
 * @author Anntinu Josy
 * 
 * @version 1.0
 *
 */
public class WeatherPredictionException extends Exception {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(WeatherPredictionException.class);

	/**
	 * @param msg
	 *            Passing Exception message to super class Exception
	 */
	public WeatherPredictionException(String msg) {
		super(msg);
		logger.debug("Exception occured and thrown ::::" + msg);
	}

}
