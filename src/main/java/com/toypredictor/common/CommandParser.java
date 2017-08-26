package com.toypredictor.common;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 * Command Line Parser
 * 
 * Date : 25-Aug-2017
 *
 * @author Anntinu Josy
 * @package com.toypredictor.common
 * @version 1.0
 */
public class CommandParser {

	private final static Logger logger = Logger.getLogger(CommandParser.class);
	/**
	 * Command Line arguments
	 */
	private String[] args = null;
	/**
	 * Command line options
	 */
	private static Options values;
	/**
	 * Input Features object
	 */
	private InputDetailsDTO inputWeather;

	public CommandParser(String[] args) {

		this.args = args;

		values = new Options();
		values.addOption(Constants.LATITUDE_SHORT, true, Constants.LATITUDE);
		values.addOption(Constants.LONGITUDE_SHORT, true, Constants.LONGITUDE);
		values.addOption(Constants.ELEVATION_SHORT, true, Constants.ELEVATION);
		values.addOption(Constants.TIME, true, Constants.UNIX_TIME);
		values.addOption(Constants.OUT, true, Constants.OUTPUT_FILE_LOCATION);
		values.addOption(Constants.HELP, false, Constants.HELP);

	}

	/**
	 * Method to parse command line arguments
	 * 
	 * @return parsed input features from cmdline args
	 * @throws ParseException
	 */
	public InputDetailsDTO process() throws ParseException {
		CommandLineParser parser = new BasicParser();
		CommandLine cmdline = null;
		cmdline = parser.parse(values, args);
		if (cmdline.hasOption(Constants.HELP))
			help();

		inputWeather = new InputDetailsDTO();
		inputWeather.setLatitude(Double.parseDouble(cmdline.getOptionValue(Constants.LATITUDE_SHORT)));
		inputWeather.setLongitude(Double.parseDouble(cmdline.getOptionValue(Constants.LONGITUDE_SHORT)));
		inputWeather.setElevation(Double.parseDouble(cmdline.getOptionValue(Constants.ELEVATION_SHORT)));
		inputWeather.setTimeStamp(cmdline.getOptionValue(Constants.TIME));
		inputWeather.setOutputFile(cmdline.getOptionValue(Constants.OUT));
		logger.info("Input Parameters configured");
		return inputWeather;

	}

	/**
	 * Method to print help
	 */
	public static void help() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Options", values);
	}

}
