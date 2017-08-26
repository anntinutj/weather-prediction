package com.toypredictor.common.test;

import static org.junit.Assert.*;

import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.junit.Before;
import org.junit.Test;

import com.toypredictor.common.Constants;
import com.toypredictor.common.DateTransformUtils;

/**
 * Test for Label Data Utils
 * 
 * Date : 26-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common.test
 * @version 1.0
 */
public class LabelDataUtilsTest {

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
		labelledPoint = "(0.0,[0.83,-33.8688197,151.2092955,24.5399284363,1015.5,72.23,7.0,15.0,23.0])";
		labelledPointForTemp = "(72.23,[-33.8688197,151.2092955,24.5399284363,7.0,15.0,23.0])";
		labelledPointForHumidity = "(0.83,[-33.8688197,151.2092955,24.5399284363,7.0,15.0,23.0])";
		labelledPointForPressure = "(1015.5,[-33.8688197,151.2092955,24.5399284363,7.0,15.0,23.0])";

	}

	/**
	 * /** Test method for function creating LabelledPointData for
	 * classification
	 */
	@Test
	public void testLabelDataPointsForClassifier() {
		String[] inputData = testInput.split(Constants.DELIMITTER_COMA);

		LabeledPoint b = new LabeledPoint(0, Vectors.dense(Double.parseDouble(inputData[Constants.HUMIDITY_POS]),
				Double.parseDouble(inputData[Constants.LAT_POS]), Double.parseDouble(inputData[Constants.LONG_POS]),
				Double.parseDouble(inputData[Constants.ELEVATION_POS]),
				Double.parseDouble(inputData[Constants.PRESSURE_POS]),
				Double.parseDouble(inputData[Constants.TEMPERATURE_POS]),
				DateTransformUtils.getMonth(inputData[Constants.TIME_POS]),
				DateTransformUtils.getDayOfMonth(inputData[Constants.TIME_POS]),
				DateTransformUtils.getHour(inputData[Constants.TIME_POS])));
		assertEquals(b.toString(), labelledPoint);

	}

	/**
	 * Test method for function creating LabelledPointForTemprature
	 */
	@Test
	public void testLabelDataPointsForForTemp() {
		String[] inputData = testInput.split(Constants.DELIMITTER_COMA);

		LabeledPoint lb = new LabeledPoint(Double.parseDouble(inputData[Constants.TEMPERATURE_POS]),
				Vectors.dense(Double.parseDouble(inputData[Constants.LAT_POS]),
						Double.parseDouble(inputData[Constants.LONG_POS]),
						Double.parseDouble(inputData[Constants.ELEVATION_POS]),
						DateTransformUtils.getMonth(inputData[Constants.TIME_POS]),
						DateTransformUtils.getDayOfMonth(inputData[Constants.TIME_POS]),
						DateTransformUtils.getHour(inputData[Constants.TIME_POS])));

		assertEquals(lb.toString(), labelledPointForTemp);

	}

	/**
	 * Test method for function creating LabelledPointForHumidity
	 */
	@Test
	public void testLabelDataPointsForHumidity() {
		String[] inputData = testInput.split(Constants.DELIMITTER_COMA);
		/*
		 * The features to be included for humidity prediction are specified
		 * here.
		 */
		LabeledPoint lb = new LabeledPoint(Double.parseDouble(inputData[Constants.HUMIDITY_POS]),
				Vectors.dense(Double.parseDouble(inputData[Constants.LAT_POS]),
						Double.parseDouble(inputData[Constants.LONG_POS]),
						Double.parseDouble(inputData[Constants.ELEVATION_POS]),
						DateTransformUtils.getMonth(inputData[Constants.TIME_POS]),
						DateTransformUtils.getDayOfMonth(inputData[Constants.TIME_POS]),
						DateTransformUtils.getHour(inputData[Constants.TIME_POS])));
		assertEquals(lb.toString(), labelledPointForHumidity);
	}

	/**
	 * Test method for function creating LabelledPointForPressure
	 */
	@Test
	public void testLabelDataPointsForPressure() {
		String[] inputData = testInput.split(Constants.DELIMITTER_COMA);
		/*
		 * The features to be included for pressure prediction are specified
		 * here.
		 */
		LabeledPoint lb = new LabeledPoint(Double.parseDouble(inputData[Constants.PRESSURE_POS]),
				Vectors.dense(Double.parseDouble(inputData[Constants.LAT_POS]),
						Double.parseDouble(inputData[Constants.LONG_POS]),
						Double.parseDouble(inputData[Constants.ELEVATION_POS]),
						DateTransformUtils.getMonth(inputData[Constants.TIME_POS]),
						DateTransformUtils.getDayOfMonth(inputData[Constants.TIME_POS]),
						DateTransformUtils.getHour(inputData[Constants.TIME_POS])));
		assertEquals(lb.toString(), labelledPointForPressure);
	}

}
