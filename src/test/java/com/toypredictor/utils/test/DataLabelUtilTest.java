package com.toypredictor.utils.test;

import static org.junit.Assert.assertEquals;

import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.junit.Before;
import org.junit.Test;

import com.toypredictor.util.constants.Constants;
import com.toypredictor.util.constants.NumericMapping;
import com.toypredictor.utils.CommonUtils;

/**
 * Test class for Datalabel Utility
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
public class DataLabelUtilTest {

	/**
	 * Input reading
	 */
	private String testInput;

	/**
	 * Expected labeled point output
	 */
	private String labelledPoint;
	/**
	 * Expected labeled point for temperature
	 */
	private String labelledPointForTemp;
	/**
	 * Expected labeled point for humidity
	 */
	private String labelledPointForHumidity;
	/**
	 * Expected labeled point for pressure
	 */
	private String labelledPointForPressure;

	@Before
	public void loadData() {
		testInput = "Sunny,0.83,-33.8688197,151.2092955,24.5399284363,1015.5,72.23,1502818365";
		labelledPoint = "(0.0,[0.83,-33.8688197,151.2092955,24.5399284363,1015.5,72.23,7.0,23.0])";
		labelledPointForTemp = "(72.23,[-33.8688197,151.2092955,24.5399284363,7.0,23.0])";
		labelledPointForHumidity = "(0.83,[-33.8688197,151.2092955,24.5399284363,7.0,23.0])";
		labelledPointForPressure = "(1015.5,[-33.8688197,151.2092955,24.5399284363,7.0,23.0])";

	}

	/**
	 * /** Test method for function creating LabelledPointData for
	 * classification
	 */
	@Test
	public void testLabelDataPointsForClassifier() {
		String[] parts = testInput.split(Constants.DELIMITTER_COMA);

		LabeledPoint b = new LabeledPoint(0, Vectors.dense(Double.parseDouble(parts[NumericMapping.HUMIDITY_POS]),
				Double.parseDouble(parts[NumericMapping.LAT_POS]), Double.parseDouble(parts[NumericMapping.LONG_POS]),
				Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
				Double.parseDouble(parts[NumericMapping.PRESSURE_POS]),
				Double.parseDouble(parts[NumericMapping.TEMPERATURE_POS]),
				CommonUtils.getMonth(parts[NumericMapping.TIME_POS]),
				CommonUtils.getHour(parts[NumericMapping.TIME_POS])));
		assertEquals(b.toString(), labelledPoint);

	}

	/**
	 * Test method for function creating LabelledPointForTemprature
	 */
	@Test
	public void testLabelDataPointsForForTemp() {
		String[] parts = testInput.split(Constants.DELIMITTER_COMA);

		LabeledPoint lb = new LabeledPoint(Double.parseDouble(parts[NumericMapping.TEMPERATURE_POS]),
				Vectors.dense(Double.parseDouble(parts[NumericMapping.LAT_POS]),
						Double.parseDouble(parts[NumericMapping.LONG_POS]),
						Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
						CommonUtils.getMonth(parts[NumericMapping.TIME_POS]),
						CommonUtils.getHour(parts[NumericMapping.TIME_POS])));

		assertEquals(lb.toString(), labelledPointForTemp);

	}

	/**
	 * Test method for function creating LabelledPointForHumidity
	 */
	@Test
	public void testLabelDataPointsForHumidity() {
		String[] parts = testInput.split(Constants.DELIMITTER_COMA);
		/*
		 * The features to be included for humidity prediction are specified
		 * here.
		 */
		LabeledPoint lb = new LabeledPoint(Double.parseDouble(parts[NumericMapping.HUMIDITY_POS]),
				Vectors.dense(Double.parseDouble(parts[NumericMapping.LAT_POS]),
						Double.parseDouble(parts[NumericMapping.LONG_POS]),
						Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
						CommonUtils.getMonth(parts[NumericMapping.TIME_POS]),
						CommonUtils.getHour(parts[NumericMapping.TIME_POS])));
		assertEquals(lb.toString(), labelledPointForHumidity);
	}

	/**
	 * Test method for function creating LabelledPointForPressure
	 */
	@Test
	public void testLabelDataPointsForPressure() {
		String[] parts = testInput.split(Constants.DELIMITTER_COMA);
		/*
		 * The features to be included for pressure prediction are specified
		 * here.
		 */
		LabeledPoint lb = new LabeledPoint(Double.parseDouble(parts[NumericMapping.PRESSURE_POS]),
				Vectors.dense(Double.parseDouble(parts[NumericMapping.LAT_POS]),
						Double.parseDouble(parts[NumericMapping.LONG_POS]),
						Double.parseDouble(parts[NumericMapping.ELEVATION_POS]),
						CommonUtils.getMonth(parts[NumericMapping.TIME_POS]),
						CommonUtils.getHour(parts[NumericMapping.TIME_POS])));
		assertEquals(lb.toString(), labelledPointForPressure);
	}

}
