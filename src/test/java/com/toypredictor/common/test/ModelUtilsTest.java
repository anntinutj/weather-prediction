package com.toypredictor.common.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import com.toypredictor.common.Constants;
import com.toypredictor.common.ModelUtils;
import com.toypredictor.common.enums.WeatherCondition;
import com.toypredictor.common.enums.WeatherParams;
import com.toypredictor.model.RandomForestClassificationModel;
import com.toypredictor.model.RandomForestRegressionModel;

/**
 * Test class for model specific utility classes
 * 
 * Date : 26-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common.test
 * @version 1.0
 */
public class ModelUtilsTest {

	/**
	 * dummy double value
	 */
	private static double testDouble;
	/**
	 * Random Forest Classifier model
	 */
	private static RandomForestClassificationModel classificationModel;
	/**
	 * Random Forest Regression model
	 */
	private static RandomForestRegressionModel regressionModel;
	/**
	 * Resource bundle
	 */
	private static ResourceBundle rb;
	/**
	 * Delta value for unit floating comparisons
	 */
	private static double delta;

	@Before
	public void loadData() {
		testDouble = -1;
		classificationModel = ModelUtils.populateModel(new RandomForestClassificationModel());
		regressionModel = ModelUtils.populateModel(new RandomForestRegressionModel(), WeatherParams.TEMPERATURE);
		rb = ResourceBundle.getBundle(Constants.MODEL_RESOURCE_BUNDLE);

	}

	/**
	 * Test method for find weather condition.
	 */
	@Test
	public void testgetWeatherCondition() {
		assertEquals(ModelUtils.getWeatherCondition(Constants.NUM_MAP_SUNNY), WeatherCondition.SUNNY);
		assertEquals(ModelUtils.getWeatherCondition(Constants.NUM_MAP_RAIN), WeatherCondition.RAIN);
		assertEquals(ModelUtils.getWeatherCondition(Constants.NUM_MAP_SNOW), WeatherCondition.SNOW);
		assertEquals(ModelUtils.getWeatherCondition(testDouble), null);
	}

	/**
	 * Test method for populating model parameters for Classification .
	 */
	@Test
	public void testPopulateModelRandomForestClassifierModel() {
		assertEquals(classificationModel.getTrainDataSize(),
				Double.parseDouble(rb.getString(Constants.DATASET_TRAINING_SIZE)), delta);
		assertEquals(classificationModel.getTestDataSize(),
				Double.parseDouble(rb.getString(Constants.DATASET_TEST_SIZE)), delta);
		assertEquals(classificationModel.getCategoricalFeaturesInfo(), new HashMap<Integer, Integer>());
		assertEquals(classificationModel.getImpurity(), rb.getString(Constants.CLASSIFICATION_IMPURITY));
		assertEquals(classificationModel.getFeatureSubsetStrategy(),
				rb.getString(Constants.CLASSIFICATION_FEATURE_SUBSET_STRATEGY));
		assertEquals(classificationModel.getMaxBins(),
				Integer.parseInt(rb.getString(Constants.CLASSIFICATION_MAX_BINS)), delta);
		assertEquals(classificationModel.getMaxDepth(),
				Integer.parseInt(rb.getString(Constants.CLASSIFICATION_MAX_DEPTH)), delta);
		assertEquals(classificationModel.getNumClasses(),
				Integer.parseInt(rb.getString(Constants.CLASSIFICATION_NUM_CLASSES)), delta);
		assertEquals(classificationModel.getNumTrees(),
				Integer.parseInt(rb.getString(Constants.CLASSIFICATION_NUM_TREES)), delta);
		assertEquals(classificationModel.getSeed(), Integer.parseInt(rb.getString(Constants.CLASSIFICATION_SEED)),
				delta);
		assertEquals(classificationModel.getModelSaveLocation(), rb.getString(Constants.CLASSIFICATION_MODEL_LOCATION));

	}

	/**
	 * Test method for populating model parameters for Regression
	 */
	@Test
	public void testPopulateModelRandomForestRegressionModelWeatherParameter() {
		assertEquals(regressionModel.getTrainDataSize(),
				Double.parseDouble(rb.getString(Constants.DATASET_TRAINING_SIZE)), delta);
		assertEquals(regressionModel.getTestDataSize(), Double.parseDouble(rb.getString(Constants.DATASET_TEST_SIZE)),
				delta);
		assertEquals(regressionModel.getCategoricalFeaturesInfo(), new HashMap<Integer, Integer>());
		assertEquals(regressionModel.getImpurity(), rb.getString(Constants.TEMPRATURE_IMPURITY));
		assertEquals(regressionModel.getMaxBins(), Integer.parseInt(rb.getString(Constants.TEMPRATURE_MAX_BINS)),
				delta);
		assertEquals(regressionModel.getMaxDepth(), Integer.parseInt(rb.getString(Constants.TEMPRATURE_MAX_DEPTH)),
				delta);
		assertEquals(regressionModel.getSeed(), Integer.parseInt(rb.getString(Constants.TEMPRATURE_SEED)), delta);
		assertEquals(regressionModel.getNumTrees(), Integer.parseInt(rb.getString(Constants.TEMPRATURE_NUM_TREES)),
				delta);
		assertEquals(regressionModel.getFeatureSubsetStrategy(),
				rb.getString(Constants.TEMPRATURE_FEATURE_SUBSET_STRATEGY));
		assertEquals(regressionModel.getModelSaveLocation(), rb.getString(Constants.TEMPRATURE_MODEL_LOCATION));
	}

}
