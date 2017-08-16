package com.toypredictor.utils;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.toypredictor.util.constants.Constants;
import com.toypredictor.util.constants.NumericMapping;

/**
 * Utility class containing different label function for Model generation.
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public class DataLabelUtil {

	/**
	 * Function to transform each input element to LabeledPoint
	 */
	public static Function<String, LabeledPoint> labelDataPointsForClassifier = new Function<String, LabeledPoint>() {

		private static final long serialVersionUID = 1L;

		public LabeledPoint call(String line) throws Exception {

			String[] parts = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for Weather condition prediction are
			 * specified here.
			 */
			return new LabeledPoint(
					Double.parseDouble(parts[NumericMapping.WEATHER_STATUS_POS]),
					Vectors.dense(
							Double.parseDouble(parts[NumericMapping.HUMIDITY_POS]),
							Double.parseDouble(parts[NumericMapping.LAT_POS]),
							Double.parseDouble(parts[NumericMapping.LONG_POS]),
							Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
							Double.parseDouble(parts[NumericMapping.PRESSURE_POS]),
							Double.parseDouble(parts[NumericMapping.TEMPERATURE_POS]),
							CommonUtils
									.getMonth(parts[NumericMapping.TIME_POS]),
							CommonUtils
									.getHour(parts[NumericMapping.TIME_POS])));
		}
	};
	
	/**
	 * Function to transform weather data to LabeledPoints for temperature
	 * prediction
	 */
	public static Function<String, LabeledPoint> labelDataPointsForForTemp = new Function<String, LabeledPoint>() {

		private static final long serialVersionUID = 1L;

		public LabeledPoint call(String line) throws Exception {

			String[] parts = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for temperature prediction are
			 * specified here.
			 */
			return new LabeledPoint(
					Double.parseDouble(parts[NumericMapping.TEMPERATURE_POS]),
					Vectors.dense(
							Double.parseDouble(parts[NumericMapping.LAT_POS]),
							Double.parseDouble(parts[NumericMapping.LONG_POS]),
							Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
							CommonUtils
									.getMonth(parts[NumericMapping.TIME_POS]),
							CommonUtils
									.getHour(parts[NumericMapping.TIME_POS])));
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

			String[] parts = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for humidity prediction are specified
			 * here.
			 */
			return new LabeledPoint(
					Double.parseDouble(parts[NumericMapping.HUMIDITY_POS]),
					Vectors.dense(
							Double.parseDouble(parts[NumericMapping.LAT_POS]),
							Double.parseDouble(parts[NumericMapping.LONG_POS]),
							Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
							CommonUtils
									.getMonth(parts[NumericMapping.TIME_POS]),
							CommonUtils
									.getHour(parts[NumericMapping.TIME_POS])));
		}
	};

	/**
	 * Function to transform weather data to LabeledPoints for pressure
	 * prediction
	 */
	public static Function<String, LabeledPoint> labelDataPointsForPressure = new Function<String, LabeledPoint>() {

		private static final long serialVersionUID = 1L;

		public LabeledPoint call(String line) throws Exception {

			String[] parts = line.split(Constants.DELIMITTER_COMA);
			/*
			 * The features to be included for pressure prediction are specified
			 * here.
			 */
			return new LabeledPoint(
					Double.parseDouble(parts[NumericMapping.PRESSURE_POS]),
					Vectors.dense(
							Double.parseDouble(parts[NumericMapping.LAT_POS]),
							Double.parseDouble(parts[NumericMapping.LONG_POS]),
							Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
							CommonUtils
									.getMonth(parts[NumericMapping.TIME_POS]),
									CommonUtils
									.getHour(parts[NumericMapping.TIME_POS])));
		}
	};
}
