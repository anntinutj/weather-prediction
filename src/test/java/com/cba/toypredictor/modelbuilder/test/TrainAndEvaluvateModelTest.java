package com.cba.toypredictor.modelbuilder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.toypredictor.modelbuilder.TrainAndEvaluvateModel;
import com.toypredictor.util.constants.Constants;
import com.toypredictor.util.constants.NumericMapping;

public class TrainAndEvaluvateModelTest {
	
	/**
	 * Input value
	 */
	private String inputData;
	/**
	 * Expected output value
	 */
	private String outputData;

	@Before
	public void loadData() {
		inputData = "Sunny,0.83,-33.8688197,151.2092955,24.5399284363,1015.5,72.23,1420030800";
		outputData = "0.0,0.83,-33.8688197,151.2092955,24.5399284363,1015.5,72.23,1420030800";

	}
	
	@Test
	public void testMain() {
		TrainAndEvaluvateModel trainAndEvaluvateModel = new TrainAndEvaluvateModel();
		assertNotEquals(trainAndEvaluvateModel.getHumidityMdl(), null);
		assertNotEquals(trainAndEvaluvateModel.getPressureMdl(), null);
		assertNotEquals(trainAndEvaluvateModel.getTemperatureMdl(), null);
		assertNotEquals(trainAndEvaluvateModel.getClassificationModel(),
				null);
	}

	/**
	 * Test method for function transformData
	 */
	@Test
	public void testTransformData() {

		String[] parts = inputData.split(Constants.DELIMITTER_COMA);
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
		StringBuilder strBuilder = new StringBuilder();

		for (int i = 0; i < parts.length; i++) {
			strBuilder.append(parts[i]);
			strBuilder.append(Constants.DELIMITTER_COMA);
		}

		// Remove extra comma
		if (strBuilder.length() > 0) {
			strBuilder.setLength(strBuilder.length() - 1);
		}

		assertEquals(strBuilder.toString(), outputData);

	}

}
