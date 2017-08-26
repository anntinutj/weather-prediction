package com.toypredictor.exceptions;

import org.apache.log4j.Logger;

/**
 * Custom exception handler for weather-prediction Project
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.exceptions
 * @version 1.0
 */
public class ToyPredictorException extends Exception {

	private static final long serialVersionUID = 4054046322261717424L;
	private final static Logger logger = Logger.getLogger(ToyPredictorException.class);

	/**
	 * Generic Exception handler for Weather Prediction
	 * 
	 * @param message
	 */
	public ToyPredictorException(String message) {
		super(message);
		logger.debug("Exception occured and thrown ::::" + message);
	}

	/**
	 * Generic Exception handler for Weather Prediction with cause
	 * 
	 * @param message
	 * @param cause
	 */
	public ToyPredictorException(String message, Throwable cause) {
		super(message, cause);
		logger.debug("Exception occured and thrown ::::" + message);
	}

}
