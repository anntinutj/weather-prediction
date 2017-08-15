package com.cba.toypredictor.app.test;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.cba.toypredictor.app.PredictWeather;

/**
 * Test class for Weather Prediction App
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
public class PredictWeatherTest {

	/**
	 * Test method to verify PredictWeather functioning
	 */
	@Test
	public void testMain() {
		PredictWeather weatherPredApp = new PredictWeather();
		assertNotEquals(weatherPredApp.getClassifierMdl(), null);
		assertNotEquals(weatherPredApp.getTemperatureMdl(), null);
		assertNotEquals(weatherPredApp.getHumidityMdl(), null);
		assertNotEquals(weatherPredApp.getPressureMdl(), null);
	}

}
