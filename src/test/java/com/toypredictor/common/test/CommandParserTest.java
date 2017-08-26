package com.toypredictor.common.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import org.apache.commons.cli.ParseException;

import com.toypredictor.common.CommandParser;

/**
 * Class for testing command line parser
 * 
 * Date : 26-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common.test
 * @version 1.0
 */
public class CommandParserTest {

	/**
	 * Command Line Helper object
	 */
	private static CommandParser cmdParser;

	@Before
	public void loadData() {
		String[] args = new String[] { "--time", "1502818365", "--lat", "33.0522342", "--long", "151.2436849", "--elev",
				"62.8470916748", "--out", "~/output.txt" };
		cmdParser = new CommandParser(args);
	}

	/**
	 * Test method for command line processor
	 * @throws ParseException 
	 */
	@Test
	public void testProcessor() throws ParseException {

		assertNotEquals(cmdParser.process(), null);
}
}
