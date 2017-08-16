package com.cba.toypredictor.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import com.toypredictor.enums.WeatherParams;
import com.toypredictor.model.DecisionTreeClassificationModel;
import com.toypredictor.model.DecisionTreeRegressionModel;
import com.toypredictor.util.constants.Constants;
import com.toypredictor.utils.ModelUtils;

/**
 * Test class for Model Utility
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
public class ModelUtilTest {
	
	/**
	 * Decision Tree Classifier model
	 */
	private static DecisionTreeClassificationModel classificationModel;
	/**
	 * Decision Tree Regression model
	 */
	private static DecisionTreeRegressionModel regressionModel;
	/**
	 * Resource bundle
	 */
	private static ResourceBundle rb;
	/**
	 * Delta value for unit floating comparisons
	 */
	private static double delta;

	/**
	 *  Load test data before testing
	 */
	@Before
	public void loadData() {
		classificationModel = ModelUtils
				.populateModelParameters(new DecisionTreeClassificationModel());
		regressionModel = ModelUtils
				.populateModelParameters(new DecisionTreeRegressionModel(),
						WeatherParams.TEMPERATURE);
		rb = ResourceBundle.getBundle(Constants.MODEL_RESOURCE_BUNDLE);
	}

	/**
	 * Test method for populating model parameters for Classification
	 * .
	 */
	@Test
	public void testPopulateModelParametersDecisionTreeClassifierModel() {
		assertEquals(classificationModel.getTrainDataSize(),
				Double.parseDouble(rb
						.getString(Constants.KEY_TRAINING_SIZE)), delta);
		assertEquals(classificationModel.getTestDataSize(),
				Double.parseDouble(rb.getString(Constants.KEY_TEST_SIZE)),
				delta);
		assertEquals(classificationModel.getCategoricalFeaturesInfo(),
				new HashMap<Integer, Integer>());
		assertEquals(classificationModel.getImpurity(),
				rb.getString(Constants.KEY_CLASSIFIER_IMPURITY));
		assertEquals(classificationModel.getMaxBins(),
				Double.parseDouble(rb
						.getString(Constants.KEY_CLASSIFIER_MAX_BIN)),
				delta);
		assertEquals(classificationModel.getMaxDepth(),
				Double.parseDouble(rb
						.getString(Constants.KEY_CLASSIFIER_MAX_DEPTH)),
				delta);
		assertEquals(
				classificationModel.getNumClasses(),
				Integer.parseInt(rb.getString(Constants.KEY_NUM_CLASSES)),
				delta);
		assertEquals(classificationModel.getModelLocation(),
				rb.getString(Constants.KEY_DECISION_TREE_MODEL_LOCATION));

	}

	/**
	 * Test method for populating model parameters for Regression
	 */
	@Test
	public void testPopulateModelParametersDecisionTreeRegressionModelWeatherParameter() {
		assertEquals(regressionModel.getTrainDataSize(),
				Double.parseDouble(rb
						.getString(Constants.KEY_TRAINING_SIZE)), delta);
		assertEquals(regressionModel.getTestDataSize(),
				Double.parseDouble(rb.getString(Constants.KEY_TEST_SIZE)),
				delta);
		assertEquals(regressionModel.getCategoricalFeaturesInfo(),
				new HashMap<Integer, Integer>());
		assertEquals(regressionModel.getImpurity(),
				rb.getString(Constants.KEY_TEMP_MODEL_IMPURITY));
		assertEquals(regressionModel.getMaxBins(), Double.parseDouble(rb
				.getString(Constants.KEY_TEMP_MODEL_MAX_BIN)), delta);
		assertEquals(regressionModel.getMaxDepth(), Double.parseDouble(rb
				.getString(Constants.KEY_TEMP_MODEL_MAX_DEPTH)), delta);
		assertEquals(regressionModel.getModelLocation(),
				rb.getString(Constants.KEY_TEMP_MODEL_LOCATION));
	}

	/**
	 * Test method for getting weather data location
	 */
	@Test
	public void testGetWeatherDataLocation() {
		assertEquals(ModelUtils.getWeatherDataLocation(),
				rb.getString(Constants.WEATHER_DATA_LOCATION));
	}

}
