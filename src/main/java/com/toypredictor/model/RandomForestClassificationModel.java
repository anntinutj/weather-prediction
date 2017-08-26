package com.toypredictor.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;

import com.toypredictor.exceptions.ToyPredictorException;

import scala.Tuple2;

/**
 * Random forest Classification model More Details,
 * http://spark.apache.org/docs/1.6.0/mllib-ensembles.html
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.model
 * @version 1.0
 */
public class RandomForestClassificationModel implements Model, Serializable {

	private static final long serialVersionUID = 6861833449067593041L;
	private final static Logger logger = Logger.getLogger(RandomForestClassificationModel.class);

	/**
	 * Number of classes
	 */
	private int numClasses;

	/**
	 * Specifies which features are categorical and how many categorical values
	 * each of those features can take. This is given as a map from feature
	 * indices to feature arity (number of categories). Any features not in this
	 * map are treated as continuous.
	 */
	private Map<Integer, Integer> categoricalFeaturesInfo;
	/**
	 * Number of trees in the forest.
	 */
	private int numTrees;
	/**
	 * Number of features to use as candidates for splitting at each tree node.
	 * The number is specified as a fraction or function of the total number of
	 * features.
	 */
	private String featureSubsetStrategy;
	/**
	 * The node impurity is a measure of the homogeneity of the labels at the
	 * node.
	 */
	private String impurity;
	/**
	 * Maximum depth of each tree in the forest.
	 */
	private int maxDepth;
	/**
	 * Number of bins used when discretizing continuous features.
	 */
	private int maxBins;
	/**
	 * Randomness inducer for forest creation
	 */
	private int seed;
	/**
	 * Train data size
	 */
	private double trainDataSize;
	/**
	 * Test data size
	 */
	private double testDataSize;

	/**
	 * Random Forest Model
	 */
	private RandomForestModel model;
	/**
	 * Location where generated model saved.
	 */
	private String modelSaveLocation;

	/**
	 * @return the numClasses
	 */
	public int getNumClasses() {
		return numClasses;
	}

	/**
	 * @param numClasses
	 *            the numClasses to set
	 */
	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}

	/**
	 * @return the categoricalFeaturesInfo
	 */
	public Map<Integer, Integer> getCategoricalFeaturesInfo() {
		return categoricalFeaturesInfo;
	}

	/**
	 * @param categoricalFeaturesInfo
	 *            the categoricalFeaturesInfo to set
	 */
	public void setCategoricalFeaturesInfo(Map<Integer, Integer> categoricalFeaturesInfo) {
		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
	}

	/**
	 * @return the numTrees
	 */
	public int getNumTrees() {
		return numTrees;
	}

	/**
	 * @param numTrees
	 *            the numTrees to set
	 */
	public void setNumTrees(int numTrees) {
		this.numTrees = numTrees;
	}

	/**
	 * @return the featureSubsetStrategy
	 */
	public String getFeatureSubsetStrategy() {
		return featureSubsetStrategy;
	}

	/**
	 * @param featureSubsetStrategy
	 *            the featureSubsetStrategy to set
	 */
	public void setFeatureSubsetStrategy(String featureSubsetStrategy) {
		this.featureSubsetStrategy = featureSubsetStrategy;
	}

	/**
	 * @return the impurity
	 */
	public String getImpurity() {
		return impurity;
	}

	/**
	 * @param impurity
	 *            the impurity to set
	 */
	public void setImpurity(String impurity) {
		this.impurity = impurity;
	}

	/**
	 * @return the maxDepth
	 */
	public int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * @param maxDepth
	 *            the maxDepth to set
	 */
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	/**
	 * @return the maxBins
	 */
	public int getMaxBins() {
		return maxBins;
	}

	/**
	 * @param maxBins
	 *            the maxBins to set
	 */
	public void setMaxBins(int maxBins) {
		this.maxBins = maxBins;
	}

	/**
	 * @return the seed
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * @param seed
	 *            the seed to set
	 */
	public void setSeed(int seed) {
		this.seed = seed;
	}

	/**
	 * @return the trainDataSize
	 */
	public double getTrainDataSize() {
		return trainDataSize;
	}

	/**
	 * @param trainDataSize
	 *            the trainDataSize to set
	 */
	public void setTrainDataSize(double trainDataSize) {
		this.trainDataSize = trainDataSize;
	}

	/**
	 * @return the testDataSize
	 */
	public double getTestDataSize() {
		return testDataSize;
	}

	/**
	 * @param testDataSize
	 *            the testDataSize to set
	 */
	public void setTestDataSize(double testDataSize) {
		this.testDataSize = testDataSize;
	}

	/**
	 * @return the model
	 */
	public RandomForestModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(RandomForestModel model) {
		this.model = model;
	}

	/**
	 * @return the modelSaveLocation
	 */
	public String getModelSaveLocation() {
		return modelSaveLocation;
	}

	/**
	 * @param modelSaveLocation
	 *            the modelSaveLocation to set
	 */
	public void setModelSaveLocation(String modelSaveLocation) {
		this.modelSaveLocation = modelSaveLocation;
	}

	@Override
	public void trainModel(JavaRDD<LabeledPoint> trainingDataSet) throws ToyPredictorException {
		logger.info("Random forest Classification Training Started");
		model = RandomForest.trainClassifier(trainingDataSet, numClasses, categoricalFeaturesInfo, numTrees,
				featureSubsetStrategy, impurity, maxDepth, maxBins, seed);

	}

	@Override
	public double testModel(JavaRDD<LabeledPoint> testDataSet) throws ToyPredictorException {
		logger.info("Random Forest Classification Model Evaluvation Started");
		JavaPairRDD<Double, Double> predictionAndLabel = testDataSet
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(getModel().predict(p.features()), p.label());
					}
				});

		// Evaluate model on test instances and compute test error
		Double testError = 1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Boolean call(Tuple2<Double, Double> pl) {
				return !pl._1().equals(pl._2());
			}
		}).count() / testDataSet.count();

		return testError;

	}

}
