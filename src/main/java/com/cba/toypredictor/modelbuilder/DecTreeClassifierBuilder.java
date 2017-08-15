package com.cba.toypredictor.modelbuilder;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.cba.toypredictor.model.DecisionTreeClassificationMdl;
import com.cba.toypredictor.util.constants.Constants;
import com.cba.toypredictor.util.constants.NumericMapping;
import com.cba.toypredictor.utils.DataLabelUtil;
import com.cba.toypredictor.utils.ModelUtils;

/**
 * Decision Tree Classification Model Builder
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
 */
public class DecTreeClassifierBuilder {

	private final static Logger logger = Logger.getLogger(DecTreeClassifierBuilder.class);
	/**
	 * Decision Tree Machine Learning model
	 */
	private static DecisionTreeClassificationMdl classificationModel;

	/**
	 * Getter for Decision Tree Classifier
	 * 
	 * @return DecisionTreeClassification
	 */
	public DecisionTreeClassificationMdl getClassificationModel() {
		return classificationModel;
	}

	/**
	 * The static block populates the model with required ML parameters
	 */
	static {
		classificationModel = ModelUtils.populateModelParameters(new DecisionTreeClassificationMdl());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SparkConf sparkConf = new SparkConf().setAppName(Constants.DECISION_TREE_APP_NAME)
				.setMaster(Constants.LOCAL_STRING);
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);

		try {

			// Load weather data from CSV file
			JavaRDD<String> inputWeatherDataset = jsc.textFile(ModelUtils.getWeatherDataLocation());

			// Transform non-numeric features into numeric values
			JavaRDD<String> transormedDataset = inputWeatherDataset.map(transformData);

			// Convert transformed RDD to RDD of Labeled Points
			JavaRDD<LabeledPoint> labelledData = transormedDataset.map(DataLabelUtil.labelDataPointsForClassifier);

			// Split the data into training and test sets (by default 70:30)
			JavaRDD<LabeledPoint>[] splits = labelledData.randomSplit(
					new double[] { classificationModel.getTrainDataSize(), classificationModel.getTestDataSize() });
			JavaRDD<LabeledPoint> trainingData = splits[0];
			JavaRDD<LabeledPoint> testData = splits[1];

			// Train the decision tree model
			classificationModel.trainModel(trainingData);
			// Save the decision tree model
			classificationModel.getModel().save(jsc.sc(), classificationModel.getModelLocation());

			// Evaluate the decision tree classifier model on test instances and
			// compute test error
			Double testError = classificationModel.evaluateModel(testData);

			logger.info("Error Value: " + testError);
			logger.info("Decision tree model:\n" + classificationModel.getModel().toDebugString());

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

}
