package com.cba.toypredictor.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cba.toypredictor.enums.WeatherParams;
import com.cba.toypredictor.exceptions.WeatherPredictionException;
import com.cba.toypredictor.model.DecisionTreeClassificationMdl;
import com.cba.toypredictor.model.DecisionTreeRegressionMdl;
import com.cba.toypredictor.util.constants.Constants;

/**
 * Class with Utility Methods for Model Building
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public class ModelUtils {

	private final static Logger logger = Logger.getLogger(ModelUtils.class);

	
	/**
	 * Method to populate parameters for Decision tree Classifier
	 * 
	 * @param classificationModel
	 * @return DecisionTreeClassification
	 */
	public static DecisionTreeClassificationMdl populateModelParameters(
			DecisionTreeClassificationMdl classificationModel) {
		try {
			final ResourceBundle rb = ResourceBundle
					.getBundle(Constants.MODEL_RESOURCE_BUNDLE);
			classificationModel.setTrainDataSize(Double.parseDouble(rb
					.getString(Constants.KEY_TRAINING_SIZE)));
			classificationModel.setTestDataSize(Double.parseDouble(rb
					.getString(Constants.KEY_TEST_SIZE)));
			/**
			 * Empty categoricalFeaturesInfo indicates all features are
			 * continuous.
			 */
			classificationModel
					.setCategoricalFeaturesInfo(new HashMap<Integer, Integer>());
			classificationModel.setImpurity(rb
					.getString(Constants.KEY_CLASSIFIER_IMPURITY));
			classificationModel.setMaxBins(Integer.parseInt(rb
					.getString(Constants.KEY_CLASSIFIER_MAX_BIN)));
			classificationModel.setMaxDepth(Integer.parseInt(rb
					.getString(Constants.KEY_CLASSIFIER_MAX_DEPTH)));
			classificationModel.setNumClasses(Integer.parseInt(rb
					.getString(Constants.KEY_NUM_CLASSES)));
			classificationModel
					.setModelLocation(rb
							.getString(Constants.KEY_DECISION_TREE_MODEL_LOCATION));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return classificationModel;
	}

	
	/**
	 * Method to populate the parameters for Decision Tree Regression Model
	 * 
	 * @param decisionTreeRegressionModel
	 * @param weatherParameter
	 * @return
	 */
	public static DecisionTreeRegressionMdl populateModelParameters(
			DecisionTreeRegressionMdl decisionTreeRegressionModel,
			WeatherParams weatherParameter) {
		try {
			final ResourceBundle rb = ResourceBundle
					.getBundle(Constants.MODEL_RESOURCE_BUNDLE);
			decisionTreeRegressionModel.setTrainDataSize(Double.parseDouble(rb
					.getString(Constants.KEY_TRAINING_SIZE)));
			decisionTreeRegressionModel.setTestDataSize(Double.parseDouble(rb
					.getString(Constants.KEY_TEST_SIZE)));
			/**
			 * Empty categoricalFeaturesInfo indicates all features are
			 * continuous.
			 */
			decisionTreeRegressionModel
					.setCategoricalFeaturesInfo(new HashMap<Integer, Integer>());

			switch (weatherParameter) {

			case TEMPERATURE:
				decisionTreeRegressionModel.setImpurity(rb
						.getString(Constants.KEY_TEMP_MODEL_IMPURITY));
				decisionTreeRegressionModel.setMaxBins(Integer.parseInt(rb
						.getString(Constants.KEY_TEMP_MODEL_MAX_BIN)));
				decisionTreeRegressionModel.setMaxDepth(Integer.parseInt(rb
						.getString(Constants.KEY_TEMP_MODEL_MAX_DEPTH)));
				decisionTreeRegressionModel.setModelLocation(rb
						.getString(Constants.KEY_TEMP_MODEL_LOCATION));
				break;
			case HUMIDITY:
				decisionTreeRegressionModel.setImpurity(rb
						.getString(Constants.KEY_HUMIDITY_MODEL_IMPURITY));
				decisionTreeRegressionModel.setMaxBins(Integer.parseInt(rb
						.getString(Constants.KEY_HUMIDITY_MODEL_MAX_BIN)));
				decisionTreeRegressionModel
						.setMaxDepth(Integer.parseInt(rb
								.getString(Constants.KEY_HUMIDITY_MODEL_MAX_DEPTH)));
				decisionTreeRegressionModel.setModelLocation(rb
						.getString(Constants.KEY_HUMIDITY_MODEL_LOCATION));
				break;
			case PRESSURE:
				decisionTreeRegressionModel.setImpurity(rb
						.getString(Constants.KEY_PRESSURE_MODEL_IMPURITY));
				decisionTreeRegressionModel.setMaxBins(Integer.parseInt(rb
						.getString(Constants.KEY_PRESSURE_MODEL_MAX_BIN)));
				decisionTreeRegressionModel
						.setMaxDepth(Integer.parseInt(rb
								.getString(Constants.KEY_PRESSURE_MODEL_MAX_DEPTH)));
				decisionTreeRegressionModel.setModelLocation(rb
						.getString(Constants.KEY_PRESSURE_MODEL_LOCATION));
				break;

			default:
				throw new WeatherPredictionException("Invalid weather parameter");

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return decisionTreeRegressionModel;
	}

	
	/**
	 * Method to retrieve location information of input  data
	 * @return location
	 */
	public static String getWeatherDataLocation() {
		try {
			Locale locale = new Locale("en", "IN");
			final ResourceBundle rb = ResourceBundle
					.getBundle(Constants.MODEL_RESOURCE_BUNDLE, locale);
			return (rb.getString(Constants.WEATHER_DATA_LOCATION));
		} catch (Exception e) {

			logger.error(e.getMessage());
			return null;
		}
	}
}