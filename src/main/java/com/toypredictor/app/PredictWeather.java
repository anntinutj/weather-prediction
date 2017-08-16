package com.toypredictor.app;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

import com.toypredictor.dto.WeatherInput;
import com.toypredictor.dto.WeatherOutput;
import com.toypredictor.enums.WeatherParams;
import com.toypredictor.exceptions.WeatherPredictionException;
import com.toypredictor.model.DecisionTreeClassificationModel;
import com.toypredictor.model.DecisionTreeRegressionModel;
import com.toypredictor.util.constants.Constants;
import com.toypredictor.utils.CommandParser;
import com.toypredictor.utils.CommonUtils;
import com.toypredictor.utils.ModelUtils;

/**
 * Class for predicting the weather based on generated model and input values.
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
public class PredictWeather {

	private final static Logger logger = Logger.getLogger(PredictWeather.class);
	/**
	 * Regression model for Temperature Prediction
	 */
	private static DecisionTreeRegressionModel temperatureModel;
	/**
	 * Regression model for Humidity Prediction
	 */
	private static DecisionTreeRegressionModel humidityModel;
	/**
	 * Regression model for Pressure Prediction
	 */
	private static DecisionTreeRegressionModel pressureModel;
	/**
	 * Classifier model for Weather Prediction
	 */
	private static DecisionTreeClassificationModel classifierModel;

	/**
	 * Getter for Temperature Model
	 * 
	 * @return DecisionTreeRegressionMdl for Temperature
	 */
	public DecisionTreeRegressionModel getTemperatureModel() {
		return temperatureModel;
	}

	/**
	 * Getter for Humidity Model
	 * 
	 * @return DecisionTreeRegressionMdl for Humidity
	 */
	public DecisionTreeRegressionModel getHumidityModel() {
		return humidityModel;
	}

	/**
	 * Getter for Pressure Model
	 * 
	 * @return DecisionTreeRegressionMdl for Pressure
	 */
	public DecisionTreeRegressionModel getPressureModel() {
		return pressureModel;
	}

	/**
	 * Getter for Classification Model
	 * 
	 * @return DecisionTreeClassificationMdl weather Condition
	 */
	public DecisionTreeClassificationModel getClassifierModel() {
		return classifierModel;
	}

	/**
	 * Input Features from command line
	 */
	private static WeatherInput inputFeatures;
	/**
	 * Predicted Weather output
	 */
	private static WeatherOutput weatherDTO;

	/**
	 * The static block loads and populate all the data for the required models
	 * 
	 */
	static {
		temperatureModel = ModelUtils.populateModelParameters(new DecisionTreeRegressionModel(), WeatherParams.TEMPERATURE);
		humidityModel = ModelUtils.populateModelParameters(new DecisionTreeRegressionModel(), WeatherParams.HUMIDITY);
		pressureModel = ModelUtils.populateModelParameters(new DecisionTreeRegressionModel(), WeatherParams.PRESSURE);
		classifierModel = ModelUtils.populateModelParameters(new DecisionTreeClassificationModel());
		weatherDTO = new WeatherOutput();
	}

	/**
	 * Main method for weather prediction
	 * 
	 * @param args
	 * @throws WeatherPredException
	 */
	public static void main(String[] args) throws WeatherPredictionException {

		SparkConf sparkConf = new SparkConf().setAppName(Constants.WEATHER_PREDICTION_APP_NAME)
				.setMaster(Constants.LOCAL_STRING);
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		inputFeatures = new CommandParser(args).process();

		try {

			// Creating input vector to predict regression
			Vector inputDataRegression = Vectors.sparse(5, new int[] { 0, 1, 2, 3, 4 },
					new double[] { inputFeatures.getLatitude(), inputFeatures.getLongitude(),
							inputFeatures.getElevation(), CommonUtils.getMonth(inputFeatures.getUnixTime()),
							CommonUtils.getHour(inputFeatures.getUnixTime()) });

			double temperature = DecisionTreeModel.load(jsc.sc(), temperatureModel.getModelLocation())
					.predict(inputDataRegression);
			weatherDTO.setTemperature(temperature);

			double humidity = DecisionTreeModel.load(jsc.sc(), humidityModel.getModelLocation())
					.predict(inputDataRegression);
			weatherDTO.setHumidity(humidity);

			double pressure = DecisionTreeModel.load(jsc.sc(), pressureModel.getModelLocation())
					.predict(inputDataRegression);
			weatherDTO.setPressure(pressure);

			// Creating input vector to predict classification
			Vector testDataClassifier = Vectors.sparse(8, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 },
					new double[] { humidity, inputFeatures.getLatitude(), inputFeatures.getLongitude(),
							inputFeatures.getElevation(), pressure, temperature,
							CommonUtils.getMonth(inputFeatures.getUnixTime()),
							CommonUtils.getHour(inputFeatures.getUnixTime()) });

			double weather = DecisionTreeModel.load(jsc.sc(), classifierModel.getModelLocation())
					.predict(testDataClassifier);

			weatherDTO.setWeatherCondition(CommonUtils.findWeatherCondition(weather));

			weatherDTO.setLocation(CommonUtils.findLocation(inputFeatures.getLatitude(), inputFeatures.getLongitude(),
					inputFeatures.getElevation()));

			weatherDTO.setLatitude(inputFeatures.getLatitude());
			weatherDTO.setLongitude(inputFeatures.getLongitude());
			weatherDTO.setElevation(inputFeatures.getElevation());
			weatherDTO.setTime(CommonUtils.timeFormatter(inputFeatures.getUnixTime()));

			// Write output to specified location
			CommonUtils.saveOutputFile(weatherDTO, inputFeatures.getOutLocation());

		} catch (Exception e) {

			logger.error(e.getMessage());
		} finally {
			jsc.close();
			jsc.stop();

		}
	}

}
