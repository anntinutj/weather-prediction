package com.toypredictor.common;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.Function;

import com.toypredictor.common.enums.WeatherCondition;
import com.toypredictor.common.enums.WeatherParams;
import com.toypredictor.exceptions.ToyPredictorException;
import com.toypredictor.model.RandomForestClassificationModel;
import com.toypredictor.model.RandomForestRegressionModel;

/**
 * Utility class MLLib data handling and conversions
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common
 * @version 1.0
 */
public class ModelUtils {

	private final static Logger logger = Logger.getLogger(ModelUtils.class);

	/**
	 * Mapping of double value to respective WeatherCondition
	 * 
	 * @param weather
	 * @return WeatherCondition
	 */
	public static WeatherCondition getWeatherCondition(double weather) {
		if (weather == Constants.NUM_MAP_SUNNY)
			return WeatherCondition.SUNNY;
		else if (weather == Constants.NUM_MAP_RAIN)
			return WeatherCondition.RAIN;
		else if (weather == Constants.NUM_MAP_SNOW)
			return WeatherCondition.SNOW;
		else
			return null;

	}

	/**
	 * Method to populate parameters for Random forest classification model.
	 * 
	 * @param model
	 * @return RandomForestClassificationModel
	 */
	public static RandomForestClassificationModel populateModel(RandomForestClassificationModel model) {
		try {
			final ResourceBundle resBundle = ResourceBundle.getBundle(Constants.MODEL_RESOURCE_BUNDLE);
			/**
			 * Empty categoricalFeaturesInfo indicates all features are
			 * continuous.
			 */
			model.setCategoricalFeaturesInfo(new HashMap<Integer, Integer>());
			model.setFeatureSubsetStrategy(resBundle.getString(Constants.CLASSIFICATION_FEATURE_SUBSET_STRATEGY));
			model.setNumClasses(Integer.parseInt(resBundle.getString(Constants.CLASSIFICATION_NUM_CLASSES)));
			model.setNumTrees(Integer.parseInt(resBundle.getString(Constants.CLASSIFICATION_NUM_TREES)));
			model.setImpurity(resBundle.getString(Constants.CLASSIFICATION_IMPURITY));
			model.setMaxBins(Integer.parseInt(resBundle.getString(Constants.CLASSIFICATION_MAX_BINS)));
			model.setMaxDepth(Integer.parseInt(resBundle.getString(Constants.CLASSIFICATION_MAX_DEPTH)));
			model.setSeed(Integer.parseInt(resBundle.getString(Constants.CLASSIFICATION_SEED)));
			model.setTestDataSize(Double.parseDouble(resBundle.getString(Constants.DATASET_TEST_SIZE)));
			model.setTrainDataSize(Double.parseDouble(resBundle.getString(Constants.DATASET_TRAINING_SIZE)));
			model.setModelSaveLocation(resBundle.getString(Constants.CLASSIFICATION_MODEL_LOCATION));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return model;
	}

	/**
	 * Method to populate parameters for Random forest regression model.
	 * 
	 * @param model
	 * @param weatherParameter
	 * @return RandomForestRegressionModel
	 */
	public static RandomForestRegressionModel populateModel(RandomForestRegressionModel model,
			WeatherParams weatherParameter) {
		try {
			final ResourceBundle resBundle = ResourceBundle.getBundle(Constants.MODEL_RESOURCE_BUNDLE);

			model.setTestDataSize(Double.parseDouble(resBundle.getString(Constants.DATASET_TEST_SIZE)));
			model.setTrainDataSize(Double.parseDouble(resBundle.getString(Constants.DATASET_TRAINING_SIZE)));
			/**
			 * Empty categoricalFeaturesInfo indicates all features are
			 * continuous.
			 */
			model.setCategoricalFeaturesInfo(new HashMap<Integer, Integer>());
			switch (weatherParameter) {

			case TEMPERATURE:
				model.setFeatureSubsetStrategy(resBundle.getString(Constants.TEMPRATURE_FEATURE_SUBSET_STRATEGY));
				model.setImpurity(resBundle.getString(Constants.TEMPRATURE_IMPURITY));
				model.setNumTrees(Integer.parseInt(resBundle.getString(Constants.TEMPRATURE_NUM_TREES)));
				model.setMaxBins(Integer.parseInt(resBundle.getString(Constants.TEMPRATURE_MAX_BINS)));
				model.setMaxDepth(Integer.parseInt(resBundle.getString(Constants.TEMPRATURE_MAX_DEPTH)));
				model.setSeed(Integer.parseInt(resBundle.getString(Constants.TEMPRATURE_SEED)));
				model.setModelSaveLocation(resBundle.getString(Constants.TEMPRATURE_MODEL_LOCATION));
				break;
			case PRESSURE:
				model.setFeatureSubsetStrategy(resBundle.getString(Constants.PRESSURE_FEATURE_SUBSET_STRATEGY));
				model.setImpurity(resBundle.getString(Constants.PRESSURE_IMPURITY));
				model.setNumTrees(Integer.parseInt(resBundle.getString(Constants.PRESSURE_NUM_TREES)));
				model.setMaxBins(Integer.parseInt(resBundle.getString(Constants.PRESSURE_MAX_BINS)));
				model.setMaxDepth(Integer.parseInt(resBundle.getString(Constants.PRESSURE_MAX_DEPTH)));
				model.setSeed(Integer.parseInt(resBundle.getString(Constants.PRESSURE_SEED)));
				model.setModelSaveLocation(resBundle.getString(Constants.PRESSURE_MODEL_LOCATION));
				break;
			case HUMIDITY:
				model.setFeatureSubsetStrategy(resBundle.getString(Constants.HUMIDITY_FEATURE_SUBSET_STRATEGY));
				model.setImpurity(resBundle.getString(Constants.HUMIDITY_IMPURITY));
				model.setNumTrees(Integer.parseInt(resBundle.getString(Constants.HUMIDITY_NUM_TREES)));
				model.setMaxBins(Integer.parseInt(resBundle.getString(Constants.HUMIDITY_MAX_BINS)));
				model.setMaxDepth(Integer.parseInt(resBundle.getString(Constants.HUMIDITY_MAX_DEPTH)));
				model.setSeed(Integer.parseInt(resBundle.getString(Constants.HUMIDITY_SEED)));
				model.setModelSaveLocation(resBundle.getString(Constants.HUMIDITY_MODEL_LOCATION));
				break;
			default:
				throw new ToyPredictorException("Invalid weather parameter");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return model;

	}

	/**
	 * Function to transform each input element converts categorical feature to
	 * numerical
	 */
	public static Function<String, String> classificationDataTransform = new Function<String, String>() {

		private static final long serialVersionUID = 1L;

		@Override
		public String call(String line) throws Exception {
			String[] parts = line.split(Constants.DELIMITTER_COMA);
			double numericValue = 0.0;
			final String weatherCondition = parts[0];
			String numericReplacedLine = null;

			// Convert categorical feature to numerical
			switch (parts[0]) {
			case Constants.SUNNY:
				numericValue = Constants.NUM_MAP_SUNNY;
				break;
			case Constants.RAIN:
				numericValue = Constants.NUM_MAP_RAIN;
				break;
			case Constants.SNOW:
				numericValue = Constants.NUM_MAP_SNOW;
				break;
			default:
				numericValue = -1;
				break;
			}
			numericReplacedLine = line.replaceAll(weatherCondition, Double.toString(numericValue));
			return numericReplacedLine;
		}
	};

}
