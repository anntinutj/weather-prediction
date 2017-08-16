package com.toypredictor.utils;

import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

import com.toypredictor.utils.CommandParser;

/**
 * Test class for Command Parser
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 */
public class CommandParserTest {

	/**
	 * Command Line Helper object
	 */
	private static CommandParser cmdParser;

	@Before
	public void loadData() {
		String[] args = new String[] { "--time", "1502818365", "--lat", "33.0522342", "--long", "151.2436849", "--ele",
				"62.8470916748", "--out", "~/output.txt" };
		cmdParser = new CommandParser(args);
	}

	/**
	 * Test method for command line processor
	 */
	@Test
	public void testProcessor() {

		assertNotEquals(cmdParser.process(), null);
	}
}
