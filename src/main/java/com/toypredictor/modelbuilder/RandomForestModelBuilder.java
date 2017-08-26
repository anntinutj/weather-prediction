package com.toypredictor.modelbuilder;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.toypredictor.common.CommonUtils;
import com.toypredictor.common.Constants;
import com.toypredictor.common.LabelDataUtils;
import com.toypredictor.common.ModelUtils;
import com.toypredictor.common.enums.WeatherParams;
import com.toypredictor.exceptions.ToyPredictorException;
import com.toypredictor.model.RandomForestClassificationModel;
import com.toypredictor.model.RandomForestRegressionModel;

/**
 * Random Forest Model Builder, Which creates model for
 * Temperature,Pressure,Humidity,Weather Condition
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.modelbuilder
 * @version 1.0
 */
public class RandomForestModelBuilder {

	private final static Logger logger = Logger.getLogger(RandomForestModelBuilder.class);
	/**
	 * Random Forest Machine Learning model
	 */
	private static RandomForestClassificationModel classificationModel;

	/**
	 * Model to predict Temperature
	 */
	private static RandomForestRegressionModel temperatureModel;
	/**
	 * Model to predict Humidity
	 */
	private static RandomForestRegressionModel humidityModel;
	/**
	 * Model to predict predict Pressure
	 */
	private static RandomForestRegressionModel pressureModel;

	/**
	 * Getter for temperature Model
	 * 
	 * @return RandomForestRegressionModel for temperature
	 */
	public RandomForestRegressionModel getTemperatureModel() {
		return temperatureModel;
	}

	/**
	 * Getter for Humidity Model
	 * 
	 * @return RandomForestRegressionModel for humidity
	 */
	public RandomForestRegressionModel getHumidityModel() {
		return humidityModel;
	}

	/**
	 * Getter for Pressure Model
	 * 
	 * @return RandomForestRegressionModel for Pressure
	 */
	public RandomForestRegressionModel getPressureModel() {
		return pressureModel;
	}

	/**
	 * Getter for Random Forest Classifier
	 * 
	 * @return RandomForestClassificationModel
	 */
	public RandomForestClassificationModel getClassificationModel() {
		return classificationModel;
	}

	/**
	 * The static block populates the model with required ML parameters
	 */
	static {
		classificationModel = ModelUtils.populateModel(new RandomForestClassificationModel());
		temperatureModel = ModelUtils.populateModel(new RandomForestRegressionModel(), WeatherParams.TEMPERATURE);
		humidityModel = ModelUtils.populateModel(new RandomForestRegressionModel(), WeatherParams.HUMIDITY);
		pressureModel = ModelUtils.populateModel(new RandomForestRegressionModel(), WeatherParams.PRESSURE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SparkConf sparkConf = new SparkConf().setAppName(Constants.MODEL_BUILDER_APP_NAME)
				.setMaster(Constants.LOCAL_STRING);
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		try {
			logger.info("Random forest Classification Model training Started");
			// Load weather data from CSV file
			JavaRDD<String> inputWeatherDataset = jsc.textFile(CommonUtils.getDatasetLocation());

			// Transform non-numeric features into numeric values
			JavaRDD<String> transormedDatasetClassification = inputWeatherDataset
					.map(ModelUtils.classificationDataTransform);

			// Convert transformed RDD to RDD of Labeled Points
			JavaRDD<LabeledPoint> labelledDatasetClassification = transormedDatasetClassification
					.map(LabelDataUtils.labelDataPointsForClassifier);

			// Split the data into training and test sets (by default 70:30)
			JavaRDD<LabeledPoint>[] datasetSplitClassification = labelledDatasetClassification.randomSplit(
					new double[] { classificationModel.getTrainDataSize(), classificationModel.getTestDataSize() });
			JavaRDD<LabeledPoint> trainingDataClassification = datasetSplitClassification[0];
			JavaRDD<LabeledPoint> testDataClassification = datasetSplitClassification[1];

			// Train the Random forest classification model
			classificationModel.trainModel(trainingDataClassification);
			// Save the Random forest classification model
			classificationModel.getModel().save(jsc.sc(), classificationModel.getModelSaveLocation());
			logger.info("Random forest Classification Model training Completed");
			logger.info("Random forest Classification Model test Started");
			// Evaluate the Random forest classification model on test instances and
			// compute test error
			Double testErrorClassification = classificationModel.testModel(testDataClassification);

			logger.info("Test Error Classification : " + testErrorClassification);
			logger.info("Random Forest Classification model:\n" + classificationModel.getModel().toDebugString());
			logger.info("Random forest Classification Model test Completed");
			logger.info("Random forest Regression Model training Started");
			JavaRDD<LabeledPoint>[] temperatureSplit = getTrainingAndTestSplit(inputWeatherDataset,
					WeatherParams.TEMPERATURE);
			JavaRDD<LabeledPoint>[] humiditySplit = getTrainingAndTestSplit(inputWeatherDataset,
					WeatherParams.HUMIDITY);
			JavaRDD<LabeledPoint>[] pressureSplit = getTrainingAndTestSplit(inputWeatherDataset,
					WeatherParams.PRESSURE);

			// Train and save the Random forest Regression models
			// Training Temperature Model
			temperatureModel.trainModel(temperatureSplit[0]);
			// Saving Temperature Model
			temperatureModel.getModel().save(jsc.sc(), temperatureModel.getModelSaveLocation());
			logger.info("Temprature Random forest Regression Model training Completed");
			// Training Humidity Model
			humidityModel.trainModel(humiditySplit[0]);
			// Saving Humidity Model
			humidityModel.getModel().save(jsc.sc(), humidityModel.getModelSaveLocation());
			logger.info("Temprature Random forest Regression Model training Completed");
			// Training Pressure Model
			pressureModel.trainModel(pressureSplit[0]);
			// Saving Pressure Model
			pressureModel.getModel().save(jsc.sc(), pressureModel.getModelSaveLocation());
			logger.info("Pressure Random forest Regression Model training Completed");
			// Evaluate each model and compute test error
			logger.info("Random forest Regression Model testing Started");
			Double temperatureTestErr = temperatureModel.testModel(temperatureSplit[1]);
			logger.info("Temperature Model MSE = " + temperatureTestErr);
			Double humidityTestErr = humidityModel.testModel(humiditySplit[1]);
			logger.info("Humidity Model MSE = " + humidityTestErr);
			Double pressureTestErr = pressureModel.testModel(pressureSplit[1]);
			logger.info("Pressure Model MSE = " + pressureTestErr);
			logger.info("Random forest Regression Model testing Completed");

		} catch (Exception e) {

			logger.error(e.getMessage());
		}
		jsc.close();
		jsc.stop();
	}

	/**
	 * To convert the weather data to Labeled points and split into training and
	 * test sets
	 * 
	 * @param weatherData
	 * @param weatherParameter
	 * @return RDD of LabeledPoint Array
	 */
	private static JavaRDD<LabeledPoint>[] getTrainingAndTestSplit(JavaRDD<String> weatherData,
			WeatherParams weatherParameter) {
		try {

			switch (weatherParameter) {
			case TEMPERATURE:
				// Convert weather RDD to RDD of Labeled Points
				JavaRDD<LabeledPoint> labelledPointForTemperature = weatherData
						.map(LabelDataUtils.labelDataPointsForForTemp);

				// Split the data into training and test sets (by default 70:30)
				JavaRDD<LabeledPoint>[] tempratureSplit = labelledPointForTemperature.randomSplit(
						new double[] { temperatureModel.getTrainDataSize(), temperatureModel.getTestDataSize() });
				return (tempratureSplit);

			case HUMIDITY:
				// Convert weather RDD to RDD of Labeled Points
				JavaRDD<LabeledPoint> labelledPointForHumidity = weatherData
						.map(LabelDataUtils.labelDataPointsForHumidity);

				// Split the data into training and test sets (by default 70:30)
				JavaRDD<LabeledPoint>[] humiditySplit = labelledPointForHumidity.randomSplit(
						new double[] { humidityModel.getTrainDataSize(), humidityModel.getTestDataSize() });
				return (humiditySplit);

			case PRESSURE:
				// Convert weather RDD to RDD of Labeled Points
				JavaRDD<LabeledPoint> labelledPointForPressure = weatherData
						.map(LabelDataUtils.labelDataPointsForPressure);

				// Split the data into training and test sets (by default 70:30)
				JavaRDD<LabeledPoint>[] pressureSplit = labelledPointForPressure.randomSplit(
						new double[] { humidityModel.getTrainDataSize(), humidityModel.getTestDataSize() });
				return (pressureSplit);

			default:
				throw new ToyPredictorException("Invalid weather parameter");

			}

		} catch (Exception e) {

			logger.error(e.getMessage());
			return null;
		}

	}
}
