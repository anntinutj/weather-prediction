package com.toypredictor.common;

/**
 * Constants for references
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common
 * @version 1.0
 */
public class Constants {

	//Spark Configuration details
	public static final String WEATHER_PREDICTION_APP_NAME = "Weather Predictor";
	public static final String MODEL_BUILDER_APP_NAME = "Model Builder";
	public static final String LOCAL_STRING = "local";

	// Command line parser helper Constants
	public static final String LATITUDE_SHORT = "lat";
	public static final String LATITUDE = "Latitude";
	public static final String LONGITUDE_SHORT = "long";
	public static final String LONGITUDE = "Longitude";
	public static final String ELEVATION_SHORT = "elev";
	public static final String ELEVATION = "Elevation";
	public static final String TIME = "time";
	public static final String UNIX_TIME = "Unix Timestamp";
	public static final String OUT = "out";
	public static final String OUTPUT_FILE_LOCATION = "Output Location";
	public static final String HELP = "help";

	//Time specific Constants
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String TIMEZONE = "UTC";
	public static final String NOT_AVAILABLE = "NA";
	
	//Weather Condition
	public static final String SUNNY = "Sunny";
	public static final String RAIN = "Rain";
	public static final String SNOW = "Snow";
	
	// Mapping non-numeric weather status to double values
	public static final double NUM_MAP_SUNNY = 0;
	public static final double NUM_MAP_RAIN = 1;
	public static final double NUM_MAP_SNOW = 2;

	// Numerical mapping of Input index position
	public static final int WEATHER_STATUS_POS = 0;
	public static final int HUMIDITY_POS = 1;
	public static final int LAT_POS = 2;
	public static final int LONG_POS = 3;
	public static final int ELEVATION_POS = 4;
	public static final int PRESSURE_POS = 5;
	public static final int TEMPERATURE_POS = 6;
	public static final int TIME_POS = 7;
	
	//Delimiters
	public static final String DELIMITTER_COMA = ",";
	public static final String DELIMITTER_PIPE = "|";
	public static final String DELIMITTER_COLON = ":";
	public static final String DELIMTER_SEMI_COLON = ";";
	public static final String DELIMTER_EQUALS = "=";
	public static final String DELIMTER_NEWLINE = "\n";
	
	//Resource file name
	public static final String MODEL_RESOURCE_BUNDLE = "model_prep";
	
	// Data Partition site
	public static final String DATASET_TRAINING_SIZE = "model.train.size";
	public static final String DATASET_TEST_SIZE = "model.test.size";
	public static final String WEATHER_DATASET_LOCATION = "model.dataset.location";
	
	//Classification Model Parameter references
	public static final String CLASSIFICATION_NUM_CLASSES = "model.classification.numClass";
	public static final String CLASSIFICATION_NUM_TREES = "model.classification.numTrees";
	public static final String CLASSIFICATION_FEATURE_SUBSET_STRATEGY = "model.classification.featureSubsetStrategy";
	public static final String CLASSIFICATION_IMPURITY = "model.classification.impurity";
	public static final String CLASSIFICATION_MAX_DEPTH = "model.classification.maxDepth";
	public static final String CLASSIFICATION_MAX_BINS = "model.classification.maxBins";
	public static final String CLASSIFICATION_SEED = "model.classification.seed";
	public static final String CLASSIFICATION_MODEL_LOCATION = "model.classification.modelSaveLocation";
	
	//Regression Model for Temprature Parameter references
	public static final String TEMPRATURE_NUM_TREES = "model.regression.temp.numTrees";
	public static final String TEMPRATURE_FEATURE_SUBSET_STRATEGY = "model.regression.temp.featureSubsetStrategy";
	public static final String TEMPRATURE_IMPURITY = "model.regression.temp.impurity";
	public static final String TEMPRATURE_MAX_DEPTH = "model.regression.temp.maxDepth";
	public static final String TEMPRATURE_MAX_BINS = "model.regression.temp.maxBins";
	public static final String TEMPRATURE_SEED = "model.regression.temp.seed";
	public static final String TEMPRATURE_MODEL_LOCATION = "model.regression.temp.modelSaveLocation" ;
	
	//Regression Model for Pressure Parameter references
	public static final String PRESSURE_NUM_TREES = "model.regression.presu.numTrees";
	public static final String PRESSURE_FEATURE_SUBSET_STRATEGY = "model.regression.presu.featureSubsetStrategy";
	public static final String PRESSURE_IMPURITY = "model.regression.presu.impurity";
	public static final String PRESSURE_MAX_DEPTH = "model.regression.presu.maxDepth";
	public static final String PRESSURE_MAX_BINS = "model.regression.presu.maxBins";
	public static final String PRESSURE_SEED = "model.regression.presu.seed";
	public static final String PRESSURE_MODEL_LOCATION = "model.regression.presu.modelSaveLocation" ;
		
	//Regression Model for Humidity Parameter references
	public static final String HUMIDITY_NUM_TREES = "model.regression.humi.numTrees";
	public static final String HUMIDITY_FEATURE_SUBSET_STRATEGY = "model.regression.humi.featureSubsetStrategy";
	public static final String HUMIDITY_IMPURITY = "model.regression.humi.impurity";
	public static final String HUMIDITY_MAX_DEPTH = "model.regression.humi.maxDepth";
	public static final String HUMIDITY_MAX_BINS = "model.regression.humi.maxBins";
	public static final String HUMIDITY_SEED = "model.regression.humi.seed";
	public static final String HUMIDITY_MODEL_LOCATION = "model.regression.humi.modelSaveLocation" ;
}
