package com.toypredictor.modelbuilder.test;

import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import com.toypredictor.modelbuilder.RandomForestModelBuilder;

/**
 * Class to test the model builder
 * 
 * Date : 26-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.modelbuilder.test
 * @version 1.0
 */
public class RandomForestModelBuilderTest {

	/**
	 * Test for checking correctness of random forest model Builder
	 */
	@Test
	public void testMain() {
		RandomForestModelBuilder randomForestBuilder = new RandomForestModelBuilder();
		assertNotEquals(randomForestBuilder.getHumidityModel(), null);
		assertNotEquals(randomForestBuilder.getPressureModel(), null);
		assertNotEquals(randomForestBuilder.getTemperatureModel(), null);
		assertNotEquals(randomForestBuilder.getClassificationModel(), null);
	}

}
