package com.cba.toypredictor.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.cba.toypredictor.dto.WeatherDTO;
import com.cba.toypredictor.enums.WeatherCondition;
import com.cba.toypredictor.util.constants.Constants;
import com.cba.toypredictor.util.constants.NumericMapping;

/**
 * Class for Common Utility methods for getting hour.
 * 
 * Date : 14-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public class CommonUtils {

	private final static Logger logger = Logger.getLogger(CommonUtils.class);

	/**
	 * Method to return hour of the day from timestamp in millis
	 * 
	 * @param timeinMillis
	 * @return hour of the day [0 ~ 23]
	 */
	public static double getHour(String timeinMillis) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(timeinMillis));
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
			calendar.setTimeInMillis(Long.parseLong(timeinMillis));
			return (double) calendar.get(Calendar.MONTH);
		} catch (Exception e) {
			logger.error("Failed to get Month from timestamp", e);
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
			calendar.setTimeInMillis(Long.parseLong(timeinMillis));
			DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			format.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
			return format.format(calendar.getTime());
		} catch (Exception e) {
			logger.error("Failed to convert epoch", e);
			return null;

		}
	}

	/**
	 * Mapping of double value to respective WeatherCondition
	 * 
	 * @param weather
	 * @return WeatherCondition
	 */
	public static WeatherCondition findWeatherStatus(double weather) {
		if (weather == NumericMapping.SUNNY)
			return WeatherCondition.SUNNY;
		else if (weather == NumericMapping.RAIN)
			return WeatherCondition.RAIN;
		else if (weather == NumericMapping.SNOW)
			return WeatherCondition.SNOW;
		else
			return null;

	}

	/**
	 * Method to return location name, Currently not integrated with any
	 * location providing service, Can be integrated later.
	 * 
	 * @param latitude
	 * @param longitude
	 * @param elevation
	 * @return Location Name
	 */
	public static String findLocation(double latitude, double longitude, double elevation) {
		// TODO: Integrate with location finder APIs. eg. Google Maps Geocoding
		return Constants.NOT_AVAILABLE;
	}

	/**
	 * Write WeatherDTO Object in corresponding format to File.
	 * 
	 * @param weatherDTO
	 * @param outputLocation
	 * @return boolean Success/Failure
	 */
	public static boolean saveOutputFile(WeatherDTO weatherDTO, String outputLocation) {
		boolean returnBoolean = false;
		Writer writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(outputLocation));

			String output = weatherDTO.getLocation() + Constants.DELIMITTER_PIPE + weatherDTO.getLatitude()
					+ Constants.DELIMITTER_COMA + weatherDTO.getLongitude() + Constants.DELIMITTER_COMA
					+ weatherDTO.getElevation() + Constants.DELIMITTER_PIPE + weatherDTO.getTime()
					+ Constants.DELIMITTER_PIPE + weatherDTO.getWeatherCondition().toString()
					+ Constants.DELIMITTER_PIPE + weatherDTO.getTemperature() + Constants.DELIMITTER_PIPE
					+ weatherDTO.getPressure() + Constants.DELIMITTER_PIPE + weatherDTO.getHumidity();

			logger.info("Output" + output);
			writer.write(output + Constants.DELIMTER_NEWLINE);
			writer.flush();
			returnBoolean = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}

		}
		return returnBoolean;

	}

}
