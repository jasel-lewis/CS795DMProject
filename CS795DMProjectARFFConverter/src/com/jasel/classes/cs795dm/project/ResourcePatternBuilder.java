package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePatternBuilder {
	static Logger logger = LogManager.getLogger(ResourcePatternBuilder.class);
	
	private BufferedWriter bw = null;
	private String filename = null;
	
	public ResourcePatternBuilder(String filename, String instanceTypeRange, String userIDRange, String hostMachineIDRange,
			String programIDRange, String fileIDRange, String resourceActionRange, String printerIDRange) throws IOException {
		this.filename = filename;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
		
		bw.append("@relation resourcedata\n\n");
		bw.append("@attribute InstanceType " + instanceTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.append("@attribute StartDateTime date YYMMDDHHmmss\n");
		bw.append("@attribute ProgramID " + programIDRange + "\n");
		bw.append("@attribute ExecutionTime numeric\n");
		bw.append("@attribute FileID " + fileIDRange + "\n");
		bw.append("@attribute Action " + resourceActionRange + "\n");
		bw.append("@attribute PrinterID " + printerIDRange + "\n");
		bw.append("@attribute Pages numeric\n\n");
		bw.append("@data\n");
		
		logger.info("Wrote ARFF header information to \"" + filename + "\"");
	}
	
	
	
	public void addDataInstance(String instance) throws IOException {
		String temp = "";
		List<String> attributes = Arrays.asList(instance.split(","));
		
		logger.debug("Instance: " + instance);
		logger.debug("Number of attributes: " + attributes.size());
		
		// InstanceType
		temp = attributes.get(0);
		logger.trace("InstanceType: " + temp);
		bw.append(temp + ",");
		
		// UserID
		temp = attributes.get(1);
		logger.trace("UserID: " + temp);
		bw.append(temp + ",");
		
		// HostMachineID
		temp = attributes.get(2);
		logger.trace("HostMachineID: " + temp);
		bw.append(temp + ",");
		
		// StartDate
		temp = convertDateFormat(attributes.get(3));
		logger.trace("StartDate (flipped to YYMMDD): " + temp);
		bw.append(temp + ",");
		
		// StartDateTime
		temp = attributes.get(4);
		logger.trace("StartTime: " + temp);
		bw.append(temp + ",");
		
		// ProgramID
		temp = attributes.get(5);
		logger.trace("ProgramID: " + temp);
		bw.append(temp + ",");
		
		// ExecutionTime
		temp = attributes.get(6);
		logger.trace("ExecutionTime: " + convertToSeconds(temp));
		bw.append(temp);
		
		try {
			// FileID
			temp = attributes.get(7);
			logger.trace("FileID: " + temp);
			bw.append(temp);
			
			// Action
			temp = attributes.get(8);
			logger.trace("Action: " + temp);
			bw.append(temp);
		} catch (IndexOutOfBoundsException ioobe) {
			bw.append(",,");
		}
		
		try {
			// PrinterID
			temp = attributes.get(9);
			logger.trace("PrinterID: " + temp);
			bw.append(temp);
			
			// Pages
			temp = attributes.get(10);
			logger.trace("Pages: " + temp);
			bw.append(temp);
		} catch (IndexOutOfBoundsException ioobe) {
			bw.append(",,");
		}
		
		bw.append("\n");
		
		// Hate to have to do it like this - thought I could trust BufferedWriter to handle itself
		// but the output was consistently cutting out at a specific spot without this next line
		bw.flush();
		
		logger.info("Sent one Resource Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
	
	
	
	/**
	 * Expects a string representing elapsed time in the format HHmmss and
	 * converts to total seconds
	 * @param time
	 * @return
	 */
	private int convertToSeconds(String time) {
		int hours = Integer.parseInt(time.substring(0, 2));
		int minutes = Integer.parseInt(time.substring(2, 4));
		int seconds = Integer.parseInt(time.substring(4));
		
		return((hours * 360) + (minutes * 60) + seconds);
	}
	
	
	
	/**
	 * The given data presents a date in the MMDDYY format.  We wish to utilize a decreasing
	 * order of chronological granularity - specifically a YYMMDD format.  This method
	 * expects a String representing a date in the MMDDYY format and returns the same but
	 * formatted as YYMMDD.
	 * @param date
	 * @return
	 */
	private String convertDateFormat(String date) {
		return (date.substring(4) + date.substring(0, 4));
	}
}