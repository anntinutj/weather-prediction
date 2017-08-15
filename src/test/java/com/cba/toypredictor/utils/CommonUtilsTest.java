package com.cba.toypredictor.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.cba.toypredictor.dto.WeatherDTO;
import com.cba.toypredictor.enums.WeatherCondition;
import com.cba.toypredictor.util.constants.Constants;
import com.cba.toypredictor.util.constants.NumericMapping;

/**
 * Test class for Common Utility
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
public class CommonUtilsTest {

	/**
	 * dummy value for Coordinate
	 */
	private static double testCoordinate;
	/**
	 * timestamp in long
	 */
	private static String timeinMilliSeconds;
	/**
	 * hour value
	 */
	private static double hour;
	/**
	 * month value
	 */
	private static double month;
	/**
	 * formatted time
	 */
	private static String formattedTime;
	/**
	 * dummy double value
	 */
	private static double testDouble;
	/**
	 * sample output
	 */
	private static WeatherDTO testOutput;

	/**
	 * sampleLocation
	 */
	private static String testLocation;
	/**
	 * Delta value for unit floating comparisons
	 */
	private static int delta;

	/**
	 * Load test data before testing
	 */
	@Before
	public void loadData() {
		testCoordinate = 0.0;
		timeinMilliSeconds = "1502818365161";
		hour = 23;
		month = 7;
		formattedTime = "2017-08-15T17:32:45Z";
		testDouble = -1;
		delta = 0;
		testLocation = "/tmp/test.txt";
		testOutput = new WeatherDTO();
		testOutput.setTime(formattedTime);
		testOutput.setLatitude(0);
		testOutput.setLongitude(0);
		testOutput.setElevation(0);
		testOutput.setHumidity(0);
		testOutput.setPressure(0);
		testOutput.setTemperature(0);
		testOutput.setWeatherCondition(WeatherCondition.SUNNY);
	}

	/**
	 * Test method for getHour
	 */
	@Test
	public void testGetHour() {
		assertEquals(CommonUtils.getHour(timeinMilliSeconds), hour, delta);
	}

	/**
	 * Test method for getMonth
	 */
	@Test
	public void testGetMonth() {
		assertEquals(CommonUtils.getMonth(timeinMilliSeconds), month, delta);
	}

	/**
	 * Test method for time formatter
	 */
	@Test
	public void testTimeFormatter() {
		assertEquals(CommonUtils.timeFormatter(timeinMilliSeconds), formattedTime);
	}

	/**
	 * Test method for find weather condition.
	 */
	@Test
	public void testFindWeatherCondition() {
		assertEquals(CommonUtils.findWeatherCondition(NumericMapping.SUNNY), WeatherCondition.SUNNY);
		assertEquals(CommonUtils.findWeatherCondition(NumericMapping.RAIN), WeatherCondition.RAIN);
		assertEquals(CommonUtils.findWeatherCondition(NumericMapping.SNOW), WeatherCondition.SNOW);
		assertEquals(CommonUtils.findWeatherCondition(testDouble), null);
	}

	/**
	 * Test method for Find location.
	 */
	@Test
	public void testFindLocation() {
		assertEquals(CommonUtils.findLocation(testCoordinate, testCoordinate, testCoordinate), Constants.NOT_AVAILABLE);
	}

	/**
	 * Test method for saving output file
	 */
	@Test
	public void testSaveOutput() {
		assertEquals(CommonUtils.saveOutputFile(null, null), false);
		assertEquals(CommonUtils.saveOutputFile(testOutput, testLocation), true);

	}

}
