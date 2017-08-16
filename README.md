# ToyWeatherPredictor
Machine Learning model for weather generation using Spark Mllib and java

## Background
To simulate an environment (taking into account atmosphere, topography, geography, or similar) that evolves over time and generate weather conditions for a specified location and time.


Machine Learning is used in this project, where weather prediction is made using Machine Learning algorithms on Spark Mllib using historical weather data. 
The machine learning approach needs a dataset. It also requires data cleaning and formatting. There are lot of api services which provides historical weather data. Some examples,
* [OpenWeatherMap](https://openweathermap.org/api)
* [WorldWeatherOnline](https://developer.worldweatheronline.com/)


Prediction of weather includes prediction of temperature,pressure,humidity and weather condition. The first three predicates are **continuous** values which needs a **regression model** for prediction. The weather condition can be either of Rain,Snow or Sunny, predicting which is a **classification problem**.

For predicting weather condition, **Decision Tree Classifier** is used.
For predicting humidity, pressure and temperature **Decision Tree Regression** is used.

Here I have used [spark.mllib](https://spark.apache.org/docs/1.6.0/mllib-guide.html) library for the linear regression, Decision Tree Regression and Decision tree classifier. Spark Provides support of big data frameworks, which would help production level deployment


## Prerequisite

[Java 1.7](https://java.com/en/download/) and [Apache Spark 1.6.0](https://spark.apache.org/releases/spark-release-1-6-0.html) must be installed in the system.

## Getting Started
To run the application, 
Change PopertyFile **model_prep.properties** accordingly

Build the maven project

```
mvn clean install  
```
![alt text](pics/BuildingProject.png)

## Run the project
Follow the steps to get output  

To Train & Evaluvate Models
```
spark-submit --class com.toypredictor.modelbuilder.TrainAndEvaluvateModel <jarlocation>
eg: spark-submit --class com.toypredictor.modelbuilder.TrainAndEvaluvateModel weather-prediction-0.0.1-SNAPSHOT.jar

```

To Predict Weather
```
spark-submit --com.toypredictor.app.PredictWeather <jarlocation> --lat <latitude> --long  <longitude> --ele  <elevation> --time <unixTimeStamp> --out <outputLocation>

eg: spark-submit --class com.toypredictor.app.PredictWeather weather-prediction-0.0.1-SNAPSHOT.jar --lat 24.8614622 --long 67.0099388 --ele 9.870092392 --time 1423123200 --out /home/usr/output.txt
```

**Result**

![alt text](pics/OutPut.png)

```
## Command line arguments 

![alt text](pics/runOption.png)

## JAVA DOCS

[JAVA DOCS](https://anntinutj.github.io/weather-prediction/)

