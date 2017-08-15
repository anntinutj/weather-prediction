package com.cba.toypredictor.modelbuilder;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.cba.toypredictor.enums.WeatherParams;
import com.cba.toypredictor.exceptions.WeatherPredictionException;
import com.cba.toypredictor.model.DecisionTreeRegressionMdl;
import com.cba.toypredictor.util.constants.Constants;
import com.cba.toypredictor.utils.DataLabelUtil;
import com.cba.toypredictor.utils.ModelUtils;

/**
 * Decision Tree Regression Model Builder
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public class DecTreeRegressionBuilder {
	
	private final static Logger logger = Logger.getLogger(DecTreeRegressionBuilder.class);

	/**
	 * Model to predict Temperature
	 */
	private static DecisionTreeRegressionMdl temperatureMdl;
	/**
	 * Model to predict Humidity
	 */
	private static DecisionTreeRegressionMdl humidityMdl;
	/**
	 * Model to predict predict Pressure
	 */
	private static DecisionTreeRegressionMdl pressureMdl;

	
	
	/**
	 * Getter for temperature Model
	 * @return DecisionTreeRegressionMdl for temperature
	 */
	public static DecisionTreeRegressionMdl getTemperatureMdl() {
		return temperatureMdl;
	}

	/**
	 * Getter for Humidity Model
	 * @return DecisionTreeRegressionMdl for humidity
	 */
	public static DecisionTreeRegressionMdl getHumidityMdl() {
		return humidityMdl;
	}

	/**
	 * Getter for Pressure Model
	 * @return DecisionTreeRegressionMdl for Pressure
	 */
	public static DecisionTreeRegressionMdl getPressureMdl() {
		return pressureMdl;
	}

	/**
	 * The static block populates the models with required ML parameters
	 */
	static {
		temperatureMdl = ModelUtils
				.populateModelParameters(new DecisionTreeRegressionMdl(),
						WeatherParams.TEMPERATURE);
		humidityMdl = ModelUtils.populateModelParameters(
				new DecisionTreeRegressionMdl(), WeatherParams.HUMIDITY);
		pressureMdl = ModelUtils.populateModelParameters(
				new DecisionTreeRegressionMdl(), WeatherParams.PRESSURE);
	}

	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf().setAppName(
				Constants.DECISION_TREE_REGRESSION_APP_NAME).setMaster(
				Constants.LOCAL_STRING);
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		try {

			// Load weather data from CSV file
			JavaRDD<String> weatherData = jsc.textFile(ModelUtils
					.getWeatherDataLocation());

			// Split weather data into training and test sets
			JavaRDD<LabeledPoint>[] temperatureSplit = getTrainingAndTestSplit(
					weatherData, WeatherParams.TEMPERATURE);
			JavaRDD<LabeledPoint>[] humiditySplit = getTrainingAndTestSplit(
					weatherData, WeatherParams.HUMIDITY);
			JavaRDD<LabeledPoint>[] pressureSplit = getTrainingAndTestSplit(
					weatherData, WeatherParams.PRESSURE);

			// Train and save the DecisionTree Regression models
			// Training Temperature Model
			temperatureMdl.trainModel(temperatureSplit[0]);
			// Saving Temperature Model
			temperatureMdl.getModel().save(jsc.sc(),
					temperatureMdl.getModelLocation());
			// Training Humidity Model
			humidityMdl.trainModel(humiditySplit[0]);
			// Saving Humidity Model
			humidityMdl.getModel().save(jsc.sc(),
					humidityMdl.getModelLocation());
			// Training Pressure Model
			pressureMdl.trainModel(pressureSplit[0]);
			// Saving Pressure Model
			pressureMdl.getModel().save(jsc.sc(),
					pressureMdl.getModelLocation());

			logger.info("Model training completed");

			// Evaluate each model and compute test error
			logger.info("Evaluating Models");
			Double temperatureTestErr = temperatureMdl
					.evaluateModel(temperatureSplit[1]);
			logger.info("Temperature Model MSE = " + temperatureTestErr);
			Double humidityTestErr = humidityMdl
					.evaluateModel(humiditySplit[1]);
			logger.info("Humidity Model MSE = " + humidityTestErr);
			Double pressureTestErr = pressureMdl
					.evaluateModel(pressureSplit[1]);
			logger.info("Pressure Model MSE = " + pressureTestErr);
			} catch (Exception e) {

			logger.error(e.getMessage());
		} finally {
			jsc.close();
			jsc.stop();

		}

	}

	/**
	 * To convert the weather data to Labeled points and split into training and
	 * test sets
	 * 
	 * @param weatherData
	 * @param weatherParameter
	 * @return RDD of LabeledPoint Array
	 */
	private static JavaRDD<LabeledPoint>[] getTrainingAndTestSplit(
			JavaRDD<String> weatherData, WeatherParams weatherParameter) {
		try {

			switch (weatherParameter) {
			case TEMPERATURE:
				// Convert weather RDD to RDD of Labeled Points
				JavaRDD<LabeledPoint> labelledPointForTemperature = weatherData
						.map(DataLabelUtil.labelDataPointsForForTemp);

				// Split the data into training and test sets (by default 7:3)
				JavaRDD<LabeledPoint>[] tempSplit = labelledPointForTemperature
						.randomSplit(new double[] {
								temperatureMdl.getTrainDataSize(),
								temperatureMdl.getTestDataSize() });
				return (tempSplit);

			case HUMIDITY:
				// Convert weather RDD to RDD of Labeled Points
				JavaRDD<LabeledPoint> labelledPointForHumidity = weatherData
						.map(DataLabelUtil.labelDataPointsForHumidity);

				// Split the data into training and test sets (by default 7:3)
				JavaRDD<LabeledPoint>[] humiditySplit = labelledPointForHumidity
						.randomSplit(new double[] {
								humidityMdl.getTrainDataSize(),
								humidityMdl.getTestDataSize() });
				return (humiditySplit);

			case PRESSURE:
				// Convert weather RDD to RDD of Labeled Points
				JavaRDD<LabeledPoint> labelledPointForPressure = weatherData
						.map(DataLabelUtil.labelDataPointsForPressure);

				// Split the data into training and test sets (by default 7:3)
				JavaRDD<LabeledPoint>[] pressureSplit = labelledPointForPressure
						.randomSplit(new double[] {
								humidityMdl.getTrainDataSize(),
								humidityMdl.getTestDataSize() });
				return (pressureSplit);

			default:
				throw new WeatherPredictionException("Invalid weather parameter");

			}

		} catch (Exception e) {

			logger.error(e.getMessage());
			return null;
		}

	}

}
