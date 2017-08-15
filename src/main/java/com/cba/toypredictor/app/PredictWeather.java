package com.cba.toypredictor.app;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

import com.cba.toypredictor.dto.WeatherDTO;
import com.cba.toypredictor.dto.WeatherInputDTO;
import com.cba.toypredictor.enums.WeatherParams;
import com.cba.toypredictor.exceptions.WeatherPredictionException;
import com.cba.toypredictor.model.DecisionTreeClassificationMdl;
import com.cba.toypredictor.model.DecisionTreeRegressionMdl;
import com.cba.toypredictor.util.constants.Constants;
import com.cba.toypredictor.utils.CommandParser;
import com.cba.toypredictor.utils.CommonUtils;
import com.cba.toypredictor.utils.ModelUtils;

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
	private static DecisionTreeRegressionMdl temperatureMdl;
	/**
	 * Regression model for Humidity Prediction
	 */
	private static DecisionTreeRegressionMdl humidityMdl;
	/**
	 * Regression model for Pressure Prediction
	 */
	private static DecisionTreeRegressionMdl pressureMdl;
	/**
	 * Classifier model for Weather Prediction
	 */
	private static DecisionTreeClassificationMdl classifierMdl;

	/**
	 * Getter for Temperature Model
	 * 
	 * @return DecisionTreeRegressionMdl for Temperature
	 */
	public DecisionTreeRegressionMdl getTemperatureMdl() {
		return temperatureMdl;
	}

	/**
	 * Getter for Humidity Model
	 * 
	 * @return DecisionTreeRegressionMdl for Humidity
	 */
	public DecisionTreeRegressionMdl getHumidityMdl() {
		return humidityMdl;
	}

	/**
	 * Getter for Pressure Model
	 * 
	 * @return DecisionTreeRegressionMdl for Pressure
	 */
	public DecisionTreeRegressionMdl getPressureMdl() {
		return pressureMdl;
	}

	/**
	 * Getter for Classification Model
	 * 
	 * @return DecisionTreeClassificationMdl weather Condition
	 */
	public DecisionTreeClassificationMdl getClassifierMdl() {
		return classifierMdl;
	}

	/**
	 * Input Features from command line
	 */
	private static WeatherInputDTO inputFeatures;
	/**
	 * Predicted Weather output
	 */
	private static WeatherDTO weatherDTO;

	/**
	 * The static block loads and populate all the data for the required models
	 * 
	 */
	static {
		temperatureMdl = ModelUtils.populateModelParameters(new DecisionTreeRegressionMdl(), WeatherParams.TEMPERATURE);
		humidityMdl = ModelUtils.populateModelParameters(new DecisionTreeRegressionMdl(), WeatherParams.HUMIDITY);
		pressureMdl = ModelUtils.populateModelParameters(new DecisionTreeRegressionMdl(), WeatherParams.PRESSURE);
		classifierMdl = ModelUtils.populateModelParameters(new DecisionTreeClassificationMdl());
		weatherDTO = new WeatherDTO();
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

			double temperature = DecisionTreeModel.load(jsc.sc(), temperatureMdl.getModelLocation())
					.predict(inputDataRegression);
			weatherDTO.setTemperature(temperature);

			double humidity = DecisionTreeModel.load(jsc.sc(), humidityMdl.getModelLocation())
					.predict(inputDataRegression);
			weatherDTO.setHumidity(humidity);

			double pressure = DecisionTreeModel.load(jsc.sc(), pressureMdl.getModelLocation())
					.predict(inputDataRegression);
			weatherDTO.setPressure(pressure);

			// Creating input vector to predict classification
			Vector testDataClassifier = Vectors.sparse(8, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 },
					new double[] { humidity, inputFeatures.getLatitude(), inputFeatures.getLongitude(),
							inputFeatures.getElevation(), pressure, temperature,
							CommonUtils.getMonth(inputFeatures.getUnixTime()),
							CommonUtils.getHour(inputFeatures.getUnixTime()) });

			double weather = DecisionTreeModel.load(jsc.sc(), classifierMdl.getModelLocation())
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
