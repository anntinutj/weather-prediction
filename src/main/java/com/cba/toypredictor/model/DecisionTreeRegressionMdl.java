package com.cba.toypredictor.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

import scala.Tuple2;

import com.cba.toypredictor.exceptions.WeatherPredictionException;


/**
 * Decision Tree Regression Model
 * 
 * Date : 14-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public class DecisionTreeRegressionMdl implements AbstractModel, Serializable {

	private static final long serialVersionUID = 1177599644173975701L;
	
	private final static Logger logger = Logger.getLogger(DecisionTreeRegressionMdl.class);

	/**
	 * training data size
	 */
	private double trainDataSize;
	/**
	 * testing data size
	 */
	private double testDataSize;
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
	public void trainModel(JavaRDD<LabeledPoint> trainingData) throws WeatherPredictionException {
		logger.info("Training Decision Tree Regression Started");
		model = DecisionTree.trainRegressor(trainingData, categoricalFeaturesInfo, impurity, maxDepth, maxBins);
	}

	@Override
	public Double evaluateModel(JavaRDD<LabeledPoint> testDataSet) throws WeatherPredictionException {
		logger.info("Evaluvating Decision Tree Regression Model Started");
		// Evaluate model on test instances and compute test error
		JavaPairRDD<Double, Double> predictionAndLabel = testDataSet
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Tuple2<Double, Double> call(LabeledPoint point) {
						return new Tuple2<Double, Double>(model.predict(point.features()), point.label());
					}
				});

		Double testMSE = predictionAndLabel.map(new Function<Tuple2<Double, Double>, Double>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Double call(Tuple2<Double, Double> pl) {
				Double diff = pl._1() - pl._2();
				return diff * diff;
			}
		}).reduce(new Function2<Double, Double, Double>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Double call(Double a, Double b) {
				return a + b;
			}
		}) / (trainDataSize + testDataSize);

		return testMSE;

	}

}
