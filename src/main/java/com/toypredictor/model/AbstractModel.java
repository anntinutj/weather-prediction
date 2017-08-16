package com.toypredictor.model;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.toypredictor.exceptions.WeatherPredictionException;

/**
 * Abstract class for ML Model.
 * 
 * Date : 14-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public interface AbstractModel {

	/**
	 * For Training ML Model
	 * 
	 * @param trainingDataSet
	 * @throws WeatherPredictionException
	 */
	public void trainModel(JavaRDD<LabeledPoint> trainingDataSet) throws WeatherPredictionException;

	/**
	 * For evaluating a ML Model
	 * 
	 * @param testDataSet
	 * @return
	 * @throws WeatherPredictionException
	 */
	public Double evaluateModel(JavaRDD<LabeledPoint> testDataSet) throws WeatherPredictionException;
}
