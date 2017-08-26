/**
 * 
 */
package com.toypredictor.common.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.toypredictor.common.DateTransformUtils;

/**
 * Test class for date transformations
 * 
 * Date : 26-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common.test
 * @version 1.0
 */
public class DateTransformUtilTest {

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
	 * hour of day value
	 */
	private static double dayOfMonth;
	/**
	 * formatted time
	 */
	private static String formattedTime;
	/**
	 * Delta value for unit floating comparisons
	 */
	private static int difference;

	/**
	 * Load test data before testing
	 */
	@Before
	public void loadData() {
		timeinMilliSeconds = "1502818365";
		hour = 23;
		dayOfMonth = 15;
		month = 7;
		formattedTime = "2017-08-15T17:32:45Z";
		difference = 0;
	}

	/**
	 * Test method for getHour
	 */
	@Test
	public void testGetHour() {
		assertEquals(DateTransformUtils.getHour(timeinMilliSeconds), hour, difference);
	}

	/**
	 * Test method for getHour
	 */
	@Test
	public void testGetDayofMonth() {
		assertEquals(DateTransformUtils.getDayOfMonth(timeinMilliSeconds), dayOfMonth, difference);
	}

	/**
	 * Test method for getMonth
	 */
	@Test
	public void testGetMonth() {
		assertEquals(DateTransformUtils.getMonth(timeinMilliSeconds), month, difference);
	}

	/**
	 * Test method for time formatter
	 */
	@Test
	public void testTimeFormatter() {
		assertEquals(DateTransformUtils.timeFormatter(timeinMilliSeconds), formattedTime);
	}

}
