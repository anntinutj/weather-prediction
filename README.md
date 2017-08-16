# ToyWeatherPredictor
Machine Learning model for weather generation using Spark Mllib and java

## Background
To simulate an environment (taking into account atmosphere, topography, geography, or similar) that evolves over time and generate weather conditions for a specified location and time.

This can be achieved in multiple ways,  
1. Using Mathematical equations to predict the weather based on current weather conditions.   
2. Collect historical data and observe the change in weather based on parameters like latitude, time etc..  
3. ....(probably more)

The first approach is purely a mathematical substitution, which may need consideration of large number of parameters (say 40 variables in an equation).  
The second approach is more of solving the problem with some Machine Learning.  


#### The ML approach
The second approach is used in this project, where weather prediction is made using Machine Learning algorithms on Spark Mllib using historical weather data. 
The machine learning approach needs a dataset. It also requires data cleaning and formatting. There are lot of api services which provides historical weather data. Some examples,
* [OpenWeatherMap](https://openweathermap.org/api)
* [WorldWeatherOnline](https://developer.worldweatheronline.com/)


Prediction of weather includes prediction of temperature,pressure,humidity and weather condition. The first three predicates are **continuous** values which needs a **regression model** for prediction. The weather condition can be either of Rain,Snow or Sunny, predicting which is a **classification problem**.

For predicting weather condition, Decision Tree Classifier was used. For predicting humidity, pressure and temperature, two models were used - Linear Regression and Decision Tree Regression and models were created for the same. On comparing the results, Decision Tree Regression provided more accurate results.

Here I have used [spark.mllib](https://spark.apache.org/docs/1.6.0/mllib-guide.html) library for the linear regression, Decision Tree Regression and decision tree classifier. Spark Provides support of big data frameworks, which would help production level deployment


## Prerequisite

[Java 1.7](https://java.com/en/download/) and [Apache Spark 1.6.0](https://spark.apache.org/releases/spark-release-1-6-0.html) must be installed in the system.

## Getting Started
To run the application, 
Change PopertyFile **model_prep.properties** accordingly

Build the maven project

```
mvn clean install  
```

## Run the project
Follow the steps to get output  

To Create & Evaluvate Models
```
spark-submit --class com.cba.toypredictor.modelbuilder.DecTreeClassifierBuilder <jarlocation>
eg: spark-submit --class com.cba.toypredictor.modelbuilder.DecTreeClassifierBuilder weather-prediction-0.0.1-SNAPSHOT.jar

spark-submit --class com.cba.toypredictor.modelbuilder.DecTreeRegressionBuilder <jarlocation>
eg: spark-submit --class com.cba.toypredictor.modelbuilder.DecTreeRegressionBuilder weather-prediction-0.0.1-SNAPSHOT.jar
```

To Predict Weather
```
spark-submit --class com.cba.toypredictor.app.PredictWeather <jarlocation> --lat <latitude> --long  <longitude> --ele  <elevation> --time <unixTimeStamp> --out <outputLocation>

eg: spark-submit --class com.cba.toypredictor.app.PredictWeather weather-prediction-0.0.1-SNAPSHOT.jar --lat 34.8614622 --long 87.0099388 --ele 14.870092392 --time 1502860727 --out /home/usr/output.txt
```


```
##Command line arguments 

--help          Displays help  
--lat            **Latitiude of the location 
--long           **Longitude of the location
--ele            **Elevation of the location 
--time           **Unix TimeStamp
--out		 **Output Location


**  -> Mandatory arguments   

