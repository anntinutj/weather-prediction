package com.toypredictor.app.test;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.toypredictor.app.PredictWeather;

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
		assertNotEquals(weatherPredApp.getClassifierModel(), null);
		assertNotEquals(weatherPredApp.getTemperatureModel(), null);
		assertNotEquals(weatherPredApp.getHumidityModel(), null);
		assertNotEquals(weatherPredApp.getPressureModel(), null);
	}

}
