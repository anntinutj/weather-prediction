package com.toypredictor.modelbuilder;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.toypredictor.enums.WeatherParams;
import com.toypredictor.exceptions.WeatherPredictionException;
import com.toypredictor.model.DecisionTreeClassificationModel;
import com.toypredictor.model.DecisionTreeRegressionModel;
import com.toypredictor.util.constants.Constants;
import com.toypredictor.util.constants.NumericMapping;
import com.toypredictor.utils.DataLabelUtil;
import com.toypredictor.utils.ModelUtils;

public class TrainAndEvaluvateModel {

	private final static Logger logger = Logger.getLogger(TrainAndEvaluvateModel.class);
	/**
	 * Decision Tree Machine Learning model
	 */
	private static DecisionTreeClassificationModel classificationModel;
	
	/**
	 * Model to predict Temperature
	 */
	private static DecisionTreeRegressionModel temperatureMdl;
	/**
	 * Model to predict Humidity
	 */
	private static DecisionTreeRegressionModel humidityMdl;
	/**
	 * Model to predict predict Pressure
	 */
	private static DecisionTreeRegressionModel pressureMdl;

	
	
	/**
	 * Getter for temperature Model
	 * @return DecisionTreeRegressionMdl for temperature
	 */
	public DecisionTreeRegressionModel getTemperatureMdl() {
		return temperatureMdl;
	}

	/**
	 * Getter for Humidity Model
	 * @return DecisionTreeRegressionMdl for humidity
	 */
	public DecisionTreeRegressionModel getHumidityMdl() {
		return humidityMdl;
	}

	/**
	 * Getter for Pressure Model
	 * @return DecisionTreeRegressionMdl for Pressure
	 */
	public DecisionTreeRegressionModel getPressureMdl() {
		return pressureMdl;
	}

	/**
	 * Getter for Decision Tree Classifier
	 * 
	 * @return DecisionTreeClassification
	 */
	public DecisionTreeClassificationModel getClassificationModel() {
		return classificationModel;
	}

	/**
	 * The static block populates the model with required ML parameters
	 */
	static {
		classificationModel = ModelUtils.populateModelParameters(new DecisionTreeClassificationModel());
		temperatureMdl = ModelUtils
				.populateModelParameters(new DecisionTreeRegressionModel(),
						WeatherParams.TEMPERATURE);
		humidityMdl = ModelUtils.populateModelParameters(
				new DecisionTreeRegressionModel(), WeatherParams.HUMIDITY);
		pressureMdl = ModelUtils.populateModelParameters(
				new DecisionTreeRegressionModel(), WeatherParams.PRESSURE);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SparkConf sparkConf = new SparkConf().setAppName(Constants.MODEL_BUILDER_APP_NAME)
				.setMaster(Constants.LOCAL_STRING);
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		try {

			// Load weather data from CSV file
			JavaRDD<String> inputWeatherDataset = jsc.textFile(ModelUtils.getWeatherDataLocation());

			// Transform non-numeric features into numeric values
			JavaRDD<String> transormedDatasetClassification = inputWeatherDataset.map(transformData);

			// Convert transformed RDD to RDD of Labeled Points
			JavaRDD<LabeledPoint> labelledDataClassification = transormedDatasetClassification.map(DataLabelUtil.labelDataPointsForClassifier);

			// Split the data into training and test sets (by default 70:30)
			JavaRDD<LabeledPoint>[] splitsClassification = labelledDataClassification.randomSplit(
					new double[] { classificationModel.getTrainDataSize(), classificationModel.getTestDataSize() });
			JavaRDD<LabeledPoint> trainingDataClassification = splitsClassification[0];
			JavaRDD<LabeledPoint> testDataClassification = splitsClassification[1];

			// Train the decision tree model
			classificationModel.trainModel(trainingDataClassification);
			// Save the decision tree model
			classificationModel.getModel().save(jsc.sc(), classificationModel.getModelLocation());

			// Evaluate the decision tree classifier model on test instances and
			// compute test error
			Double testErrorClassification = classificationModel.evaluateModel(testDataClassification);

			logger.info("Test Error Classification : " + testErrorClassification);
			logger.info("Decision tree model:\n" + classificationModel.getModel().toDebugString());
			
			JavaRDD<LabeledPoint>[] temperatureSplit = getTrainingAndTestSplit(
					inputWeatherDataset, WeatherParams.TEMPERATURE);
			JavaRDD<LabeledPoint>[] humiditySplit = getTrainingAndTestSplit(
					inputWeatherDataset, WeatherParams.HUMIDITY);
			JavaRDD<LabeledPoint>[] pressureSplit = getTrainingAndTestSplit(
					inputWeatherDataset, WeatherParams.PRESSURE);

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
		}
		jsc.close();
		jsc.stop();
	}
	
	
	/**
	 * Function to transform each input element : converts categorical feature
	 * to numerical
	 */
	public static Function<String, String> transformData = new Function<String, String>() {

		private static final long serialVersionUID = 1L;

		StringBuilder strBuilder = null;

		@Override
		public String call(String line) throws Exception {
			String[] parts = line.split(Constants.DELIMITTER_COMA);
			double numericValue = 0.0;

			// Convert categorical feature to numerical
			switch (parts[0]) {
			case Constants.SUNNY:
				numericValue = NumericMapping.SUNNY;
				break;
			case Constants.RAIN:
				numericValue = NumericMapping.RAIN;
				break;
			case Constants.SNOW:
				numericValue = NumericMapping.SNOW;
				break;
			default:
				numericValue = -1;
				break;
			}
			parts[0] = Double.toString(numericValue);
			strBuilder = new StringBuilder();

			for (int i = 0; i < parts.length; i++) {
				strBuilder.append(parts[i]);
				strBuilder.append(Constants.DELIMITTER_COMA);
			}

			// Remove extra comma
			if (strBuilder.length() > 0) {
				strBuilder.setLength(strBuilder.length() - 1);
			}

			return strBuilder.toString();
		}
	};
	
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
