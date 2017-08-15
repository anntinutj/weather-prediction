package com.cba.toypredictor.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

import com.cba.toypredictor.exceptions.WeatherPredictionException;

import scala.Tuple2;

/**
 * Decision Tree Classification Model
 * 
 * Date : 14-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public class DecisionTreeClassificationMdl implements AbstractModel,Serializable{
	
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger
			.getLogger(DecisionTreeClassificationMdl.class);

	/**
	 * training data size
	 */
	private double trainDataSize;
	/**
	 * testing data size
	 */
	private double testDataSize;
	/**
	 * Number of classes
	 */
	private int numClasses;
	/**
	 * impurity
	 */
	private String impurity;
	/**
	 * Max Depth
	 */
	private int maxDepth;
	/**
	 * Max Bins
	 */
	private int maxBins;
	/**
	 * categoricalFeaturesInfo
	 */
	private Map<Integer, Integer> categoricalFeaturesInfo;
	/**
	 * Model
	 */
	private DecisionTreeModel model;
	/**
	 * modelLocation
	 */
	private String modelLocation;

	

	public double getTrainDataSize() {
		return trainDataSize;
	}

	public void setTrainDataSize(double trainDataSize) {
		this.trainDataSize = trainDataSize;
	}

	public double getTestDataSize() {
		return testDataSize;
	}

	public void setTestDataSize(double testDataSize) {
		this.testDataSize = testDataSize;
	}

	public int getNumClasses() {
		return numClasses;
	}

	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}

	public String getImpurity() {
		return impurity;
	}

	public void setImpurity(String impurity) {
		this.impurity = impurity;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public int getMaxBins() {
		return maxBins;
	}

	public void setMaxBins(int maxBins) {
		this.maxBins = maxBins;
	}

	public Map<Integer, Integer> getCategoricalFeaturesInfo() {
		return categoricalFeaturesInfo;
	}

	public void setCategoricalFeaturesInfo(Map<Integer, Integer> categoricalFeaturesInfo) {
		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
	}

	public DecisionTreeModel getModel() {
		return model;
	}

	public void setModel(DecisionTreeModel model) {
		this.model = model;
	}

	public String getModelLocation() {
		return modelLocation;
	}

	public void setModelLocation(String modelLocation) {
		this.modelLocation = modelLocation;
	}

	@Override
	public void trainModel(JavaRDD<LabeledPoint> trainingDataSet)
			throws WeatherPredictionException {
		logger.info("Decision Tree Classification Model Training Started");
		model = DecisionTree.trainClassifier(trainingDataSet, numClasses,
				categoricalFeaturesInfo, impurity, maxDepth, maxBins);
	}

	@Override
	public Double evaluateModel(JavaRDD<LabeledPoint> testDataSet)
			throws WeatherPredictionException {
		logger.info("Decision Tree Classification Model Evaluvation Started");
		JavaPairRDD<Double, Double> predictionAndLabel = testDataSet
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(getModel().predict(
								p.features()), p.label());
					}
				});

		// Evaluate model on test instances and compute test error
		Double testErr = 1.0
				* predictionAndLabel.filter(
						new Function<Tuple2<Double, Double>, Boolean>() {

							private static final long serialVersionUID = 1L;

							@Override
							public Boolean call(Tuple2<Double, Double> pl) {
								return !pl._1().equals(pl._2());
							}
						}).count() / testDataSet.count();

		return testErr;
	}

}
