package com.toypredictor.common;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

/**
 * Util class for labeling data for processing
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common
 * @version 1.0
 */
public class LabelDataUtils {

	/**
	 * Function to transform each input element to LabeledPoint
	 */
	public static Function<String, LabeledPoint> labelDataPointsForClassifier = new Function<String, LabeledPoint>() {

		private static final long serialVersionUID = 1L;

		public LabeledPoint call(String line) throws Exception {

			String[] dataSplit = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for Weather condition prediction are
			 * specified here.
			 */
			return new LabeledPoint(Double.parseDouble(dataSplit[Constants.WEATHER_STATUS_POS]),
					Vectors.dense(Double.parseDouble(dataSplit[Constants.HUMIDITY_POS]),
							Double.parseDouble(dataSplit[Constants.LAT_POS]),
							Double.parseDouble(dataSplit[Constants.LONG_POS]),
							Double.parseDouble(dataSplit[Constants.ELEVATION_POS]),
							Double.parseDouble(dataSplit[Constants.PRESSURE_POS]),
							Double.parseDouble(dataSplit[Constants.TEMPERATURE_POS]),
							DateTransformUtils.getMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getDayOfMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getHour(dataSplit[Constants.TIME_POS])));
		}
	};

	/**
	 * Function to transform weather data to LabeledPoints for temperature
	 * prediction
	 */
	public static Function<String, LabeledPoint> labelDataPointsForForTemp = new Function<String, LabeledPoint>() {

		private static final long serialVersionUID = 1L;

		public LabeledPoint call(String line) throws Exception {

			String[] dataSplit = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for temperature prediction are
			 * specified here.
			 */
			return new LabeledPoint(Double.parseDouble(dataSplit[Constants.TEMPERATURE_POS]),
					Vectors.dense(Double.parseDouble(dataSplit[Constants.LAT_POS]),
							Double.parseDouble(dataSplit[Constants.LONG_POS]),
							Double.parseDouble(dataSplit[Constants.ELEVATION_POS]),
							DateTransformUtils.getMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getDayOfMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getHour(dataSplit[Constants.TIME_POS])));
		}
	};

	/**
	 * 
	 * Function to transform weather data to LabeledPoints for humidity
	 * prediction
	 * 
	 */
	public static Function<String, LabeledPoint> labelDataPointsForHumidity = new Function<String, LabeledPoint>() {

		private static final long serialVersionUID = 1L;

		public LabeledPoint call(String line) throws Exception {

			String[] dataSplit = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for humidity prediction are specified
			 * here.
			 */
			return new LabeledPoint(Double.parseDouble(dataSplit[Constants.HUMIDITY_POS]),
					Vectors.dense(Double.parseDouble(dataSplit[Constants.LAT_POS]),
							Double.parseDouble(dataSplit[Constants.LONG_POS]),
							Double.parseDouble(dataSplit[Constants.ELEVATION_POS]),
							DateTransformUtils.getMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getDayOfMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getHour(dataSplit[Constants.TIME_POS])));
		}
	};

	/**
	 * Function to transform weather data to LabeledPoints for pressure
	 * prediction
	 */
	public static Function<String, LabeledPoint> labelDataPointsForPressure = new Function<String, LabeledPoint>() {

		private static final long serialVersionUID = 1L;

		public LabeledPoint call(String line) throws Exception {

			String[] dataSplit = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for pressure prediction are specified
			 * here.
			 */
			return new LabeledPoint(Double.parseDouble(dataSplit[Constants.PRESSURE_POS]),
					Vectors.dense(Double.parseDouble(dataSplit[Constants.LAT_POS]),
							Double.parseDouble(dataSplit[Constants.LONG_POS]),
							Double.parseDouble(dataSplit[Constants.ELEVATION_POS]),
							DateTransformUtils.getMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getDayOfMonth(dataSplit[Constants.TIME_POS]),
							DateTransformUtils.getHour(dataSplit[Constants.TIME_POS])));
		}
	};

}
