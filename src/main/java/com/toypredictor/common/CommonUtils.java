package com.toypredictor.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Util class for common utility methods
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common
 * @version 1.0
 */
public class CommonUtils {

	private final static Logger logger = Logger.getLogger(CommonUtils.class);

	/**
	 * Method to print the result to Outputfile
	 * 
	 * @param outputDetails
	 * @param outputLocation
	 * @return
	 */
	public static boolean writeOutputFile(OutputDetailsDTO outputDetails, String outputLocation) {
		boolean returnBoolean = false;
		Writer fileWriter = null;

		try {
			fileWriter = new BufferedWriter(new FileWriter(outputLocation));

			String dataOutput = outputDetails.getLocation() + Constants.DELIMITTER_PIPE + outputDetails.getLatitude()
					+ Constants.DELIMITTER_COMA + outputDetails.getLongitude() + Constants.DELIMITTER_COMA
					+ outputDetails.getElevation() + Constants.DELIMITTER_PIPE + outputDetails.getTime()
					+ Constants.DELIMITTER_PIPE + outputDetails.getWeatherCondition().toString()
					+ Constants.DELIMITTER_PIPE + outputDetails.getTemperature() + Constants.DELIMITTER_PIPE
					+ outputDetails.getPressure() + Constants.DELIMITTER_PIPE + outputDetails.getHumidity();

			logger.info("Output" + dataOutput);
			fileWriter.write(dataOutput + Constants.DELIMTER_NEWLINE);
			fileWriter.flush();
			returnBoolean = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (fileWriter != null)
				try {
					fileWriter.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}

		}
		return returnBoolean;

	}

	/**
	 * Method to return location name, Currently not integrated with any
	 * location providing service, Can be integrated later.
	 * 
	 * @param latitude
	 * @param longitude
	 * @return Location Name
	 */
	public static String getLocation(double latitude, double longitude) {
		// TODO: Integrate with location finder APIs. eg. Google Maps Geocoding
		return Constants.NOT_AVAILABLE;
	}

	/**
	 * Method to retrieve location information of input data
	 * 
	 * @return location
	 */
	public static String getDatasetLocation() {
		try {
			Locale locale = new Locale("en", "IN");
			final ResourceBundle rb = ResourceBundle.getBundle(Constants.MODEL_RESOURCE_BUNDLE, locale);
			return (rb.getString(Constants.WEATHER_DATASET_LOCATION));
		} catch (Exception e) {

			logger.error(e.getMessage());
			return null;
		}
	}

}
