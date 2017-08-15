package com.cba.toypredictor.modelbuilder.test;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Test class for Decision Tree Regression Builder
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
import com.cba.toypredictor.modelbuilder.DecTreeRegressionBuilder;

/**
 * Test class for Decission Tree Regression Builder
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
public class DecTreeRegressionBuilderTest {
	
	
	/**
	 * Test method for Main method
	 */
	@Test
	public void testMain() {
		DecTreeRegressionBuilder regressionBuilder = new DecTreeRegressionBuilder();
		assertNotEquals(regressionBuilder.getHumidityMdl(), null);
		assertNotEquals(regressionBuilder.getPressureMdl(), null);
		assertNotEquals(regressionBuilder.getTemperatureMdl(), null);

	}

}
