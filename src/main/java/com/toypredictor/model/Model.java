package com.toypredictor.model;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.toypredictor.exceptions.ToyPredictorException;

/**
 * Interface for ML Model
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.model
 * @version 1.0
 */
public interface Model {

	/**
	 * For Training ML Model
	 * 
	 * @param trainingDataSet
	 * @throws ToyPredictorException
	 */
	public void trainModel(JavaRDD<LabeledPoint> trainingDataSet) throws ToyPredictorException;

	/**
	 * For Testing the ML Model
	 * 
	 * @param trainingDataSet
	 * @throws ToyPredictorException
	 */
	public double testModel(JavaRDD<LabeledPoint> testDataSet) throws ToyPredictorException;

}
