/**
 * 
 */
package com.toypredictor.common.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.toypredictor.common.CommonUtils;
import com.toypredictor.common.Constants;
import com.toypredictor.common.OutputDetailsDTO;
import com.toypredictor.common.enums.WeatherCondition;

/**
 * Test class for common Utils
 * 
 * Date : 26-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common.test
 * @version 1.0
 */
public class CommonUtilsTest {

	/**
	 * dummy value for Coordinate
	 */
	private static double testCoordinate;
	/**
	 * sample output
	 */
	private static OutputDetailsDTO testResult;

	/**
	 * sampleLocation
	 */
	private static String testLocation;
	/**
	 * formatted time
	 */
	private static String formattedTime;

	/**
	 * Load test data before testing
	 */
	@Before
	public void loadData() {
		testCoordinate = 0.0;
		testLocation = "/tmp/test.txt";
		formattedTime = "2017-08-15T17:32:45Z";
		testResult = new OutputDetailsDTO();
		testResult.setTime(formattedTime);
		testResult.setLatitude(0);
		testResult.setLongitude(0);
		testResult.setElevation(0);
		testResult.setHumidity(0);
		testResult.setPressure(0);
		testResult.setTemperature(0);
		testResult.setWeatherCondition(WeatherCondition.SUNNY);
	}

	/**
	 * Test method for Find location.
	 */
	@Test
	public void testFindLocation() {
		assertEquals(CommonUtils.getLocation(testCoordinate, testCoordinate), Constants.NOT_AVAILABLE);
	}

	/**
	 * Test method for saving output file
	 */
	@Test
	public void testSaveOutput() {
		assertEquals(CommonUtils.writeOutputFile(null, null), false);
		assertEquals(CommonUtils.writeOutputFile(testResult, testLocation), true);

	}

}
