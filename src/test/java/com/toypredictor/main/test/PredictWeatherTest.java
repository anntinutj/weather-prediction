package com.toypredictor.main.test;

import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import com.toypredictor.main.PredictWeather;

/**
 * Test class for Predict Weather
 * 
 * Date : 26-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.main.test
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
