package com.toypredictor.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * Util class for performing date transformations.
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common
 * @version 1.0
 */
public class DateTransformUtils {

	private final static Logger logger = Logger.getLogger(DateTransformUtils.class);

	/**
	 * Method to return hour of the day from timestamp in millis
	 * 
	 * @param timeinMillis
	 * @return hour of the day [0 ~ 23]
	 */
	public static double getHour(String timeinMillis) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(timeinMillis) * 1000);
			return (double) calendar.get(Calendar.HOUR_OF_DAY);
		} catch (Exception e) {
			logger.error("Failed to get hour from timestamp", e);
			return -1;
		}

	}

	/**
	 * Method to return the month of Year
	 * 
	 * @param timeinMillis
	 * @return month [0 ~ 11]
	 */
	public static double getMonth(String timeinMillis) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(timeinMillis) * 1000);
			return (double) calendar.get(Calendar.MONTH);
		} catch (Exception e) {
			logger.error("Failed to get Month from timestamp", e);
			return -1;
		}

	}

	/**
	 * Method to return the month of Year
	 * 
	 * @param timeinMillis
	 * @return day of month [0 ~ 30]
	 */
	public static double getDayOfMonth(String timeinMillis) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(timeinMillis) * 1000);
			return (double) calendar.get(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			logger.error("Failed to get Day of Month from timestamp", e);
			return -1;
		}

	}

	/**
	 * Method for converting time in Mills to yyyy-MM-dd'T'HH:mm:ss'Z' time
	 * format.
	 * 
	 * @param timeinMillis
	 * @return date formatted as yyyy-MM-dd'T'HH:mm:ss'Z' from mills
	 */
	public static String timeFormatter(String timeinMillis) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(timeinMillis) * 1000);
			DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			format.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
			return format.format(calendar.getTime());
		} catch (Exception e) {
			logger.error("Failed to convert epoch", e);
			return null;

		}
	}

}
