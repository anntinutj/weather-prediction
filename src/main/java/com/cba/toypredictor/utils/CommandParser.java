package com.cba.toypredictor.utils;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import com.cba.toypredictor.dto.WeatherInputDTO;
import com.cba.toypredictor.util.constants.Constants;

/**
 * Helper class for parsing command line arguments
 * 
 * Date : 15-Aug-2017
 * 
 * @author Anntinu Josy
 * @version 1.0
 *
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
	private Options options;
	/**
	 * Input Features object
	 */
	private WeatherInputDTO featureset;

	public CommandParser(String[] args) {

		this.args = args;

		options = new Options();
		options.addOption(Constants.LATITUDE_SHORT, true, Constants.LATITUDE);
		options.addOption(Constants.LONGITUDE_SHORT, true, Constants.LONGITUDE);
		options.addOption(Constants.ELEVATION_SHORT, true, Constants.ELEVATION);
		options.addOption(Constants.TIME, true, Constants.UNIX_TIME);
		options.addOption(Constants.OUT, true, Constants.OUTPUT_FILE_LOCATION);
		options.addOption(Constants.HELP, false, Constants.HELP);

	}

	/**
	 * Method to parse command line arguments
	 * 
	 * @return parsed input features from cmdline args
	 */
	public WeatherInputDTO process() {
		CommandLineParser parser = new BasicParser();
		CommandLine cmdline = null;

		try {
			cmdline = parser.parse(options, args);

			if (cmdline.hasOption(Constants.HELP))
				help();

			featureset = new WeatherInputDTO();
			featureset.setLatitude(Double.parseDouble(cmdline.getOptionValue(Constants.LATITUDE_SHORT)));
			featureset.setLongitude(Double.parseDouble(cmdline.getOptionValue(Constants.LONGITUDE_SHORT)));
			featureset.setElevation(Double.parseDouble(cmdline.getOptionValue(Constants.ELEVATION_SHORT)));
			featureset.setUnixTime(cmdline.getOptionValue(Constants.TIME));
			featureset.setOutLocation(cmdline.getOptionValue(Constants.OUT));
			return featureset;
		} catch (ParseException e) {
			logger.error("Failed to parse command line properties", e);
			help();
			return null;

		}

	}

	/**
	 * Method to print help
	 */
	private void help() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Options", options);
	}

}
