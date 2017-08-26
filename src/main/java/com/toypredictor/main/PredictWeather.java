package com.toypredictor.main;

import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.RandomForestModel;

import com.toypredictor.common.CommandParser;
import com.toypredictor.common.CommonUtils;
import com.toypredictor.common.Constants;
import com.toypredictor.common.DateTransformUtils;
import com.toypredictor.common.InputDetailsDTO;
import com.toypredictor.common.ModelUtils;
import com.toypredictor.common.OutputDetailsDTO;
import com.toypredictor.common.enums.WeatherParams;
import com.toypredictor.exceptions.ToyPredictorException;
import com.toypredictor.model.RandomForestClassificationModel;
import com.toypredictor.model.RandomForestRegressionModel;

/**
 * Class for predicting the weather based on generated model and input values.
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.main
 * @version 1.0
 */
public class PredictWeather {

	private final static Logger logger = Logger.getLogger(PredictWeather.class);
	/**
	 * Regression model for Temperature Prediction
	 */
	private static RandomForestRegressionModel temperatureModel;
	/**
	 * Regression model for Humidity Prediction
	 */
	private static RandomForestRegressionModel humidityModel;
	/**
	 * Regression model for Pressure Prediction
	 */
	private static RandomForestRegressionModel pressureModel;
	/**
	 * Classifier model for Weather Prediction
	 */
	private static RandomForestClassificationModel classifierModel;

	/**
	 * Getter for Temperature Model
	 * 
	 * @return RandomForestRegressionModel for Temperature
	 */
	public RandomForestRegressionModel getTemperatureModel() {
		return temperatureModel;
	}

	/**
	 * Getter for Humidity Model
	 * 
	 * @return RandomForestRegressionModel for Humidity
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
	 * Getter for Classification Model
	 * 
	 * @return RandomForestClassificationModel weather Condition
	 */
	public RandomForestClassificationModel getClassifierModel() {
		return classifierModel;
	}

	/**
	 * Input Features from command line
	 */
	private static InputDetailsDTO inputDetails;
	/**
	 * Predicted Weather output
	 */
	private static OutputDetailsDTO outputDetails;

	/**
	 * The static block loads and populate all the data for the required models
	 * 
	 */
	static {
		temperatureModel = ModelUtils.populateModel(new RandomForestRegressionModel(), WeatherParams.TEMPERATURE);
		humidityModel = ModelUtils.populateModel(new RandomForestRegressionModel(), WeatherParams.HUMIDITY);
		pressureModel = ModelUtils.populateModel(new RandomForestRegressionModel(), WeatherParams.PRESSURE);
		classifierModel = ModelUtils.populateModel(new RandomForestClassificationModel());
		outputDetails = new OutputDetailsDTO();
	}

	/**
	 * Main method for weather prediction
	 * 
	 * @param args
	 * @throws WeatherPredException
	 */
	public static void main(String[] args) throws ToyPredictorException {

		SparkConf sparkConf = new SparkConf().setAppName(Constants.WEATHER_PREDICTION_APP_NAME)
				.setMaster(Constants.LOCAL_STRING);
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		try {
			inputDetails = new CommandParser(args).process();
		} catch (ParseException e) {

			logger.error("Invalid Input");
			CommandParser.help();
			jsc.close();
			jsc.stop();
			System.exit(1);

		} 
		try {

			// Creating input vector to predict regression
			Vector inputDataRegression = Vectors.sparse(6, new int[] { 0, 1, 2, 3, 4, 5 },
					new double[] { inputDetails.getLatitude(), inputDetails.getLongitude(), inputDetails.getElevation(),
							DateTransformUtils.getMonth(inputDetails.getTimeStamp()),
							DateTransformUtils.getDayOfMonth(inputDetails.getTimeStamp()),
							DateTransformUtils.getHour(inputDetails.getTimeStamp()) });

			double temperature = RandomForestModel.load(jsc.sc(), temperatureModel.getModelSaveLocation())
					.predict(inputDataRegression);
			outputDetails.setTemperature(temperature);

			double humidity = RandomForestModel.load(jsc.sc(), humidityModel.getModelSaveLocation())
					.predict(inputDataRegression);
			outputDetails.setHumidity(humidity);

			double pressure = RandomForestModel.load(jsc.sc(), pressureModel.getModelSaveLocation())
					.predict(inputDataRegression);
			outputDetails.setPressure(pressure);

			// Creating input vector to predict classification
			Vector inputClassifier = Vectors.sparse(9, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 },
					new double[] { humidity, inputDetails.getLatitude(), inputDetails.getLongitude(),
							inputDetails.getElevation(), pressure, temperature,
							DateTransformUtils.getMonth(inputDetails.getTimeStamp()),
							DateTransformUtils.getDayOfMonth(inputDetails.getTimeStamp()),
							DateTransformUtils.getHour(inputDetails.getTimeStamp()) });

			double weather = RandomForestModel.load(jsc.sc(), classifierModel.getModelSaveLocation())
					.predict(inputClassifier);

			outputDetails.setWeatherCondition(ModelUtils.getWeatherCondition(weather));

			outputDetails.setLocation(CommonUtils.getLocation(inputDetails.getLatitude(), inputDetails.getLongitude()));

			outputDetails.setLatitude(inputDetails.getLatitude());
			outputDetails.setLongitude(inputDetails.getLongitude());
			outputDetails.setElevation(inputDetails.getElevation());
			outputDetails.setTime(DateTransformUtils.timeFormatter(inputDetails.getTimeStamp()));

			// Write output to specified location
			CommonUtils.writeOutputFile(outputDetails, inputDetails.getOutputFile());

		} catch (Exception e) {

			logger.error(e.getMessage());
		} finally {
			jsc.close();
			jsc.stop();

		}
	}

}
