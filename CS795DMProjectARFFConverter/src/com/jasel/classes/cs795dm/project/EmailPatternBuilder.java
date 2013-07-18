package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailPatternBuilder {
	static Logger logger = LogManager.getLogger(EmailPatternBuilder.class);
	
	private BufferedWriter bw = null;
	private String filename = null;
	
	public EmailPatternBuilder(String filename, String instanceTypeRange, String userIDRange, String hostMachineIDRange,
			String emailProgramIDRange, String emailActionRange) throws IOException {
		this.filename = filename;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
		
		bw.append("@relation emaildata\n\n");
		bw.append("@attribute InstanceType " + instanceTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.append("@attribute StartDateTime date YYMMDDHHmmss\n");
		bw.append("@attribute EmailProgramID " + emailProgramIDRange + "\n");
		bw.append("@attribute Address string\n");
		bw.append("@attribute Action " + emailActionRange + "\n");
		bw.append("@attribute Bytes numeric\n");
		bw.append("@attribute Attachments numeric\n\n");
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
		
		// EmailProgramID
		temp = attributes.get(5);
		logger.trace("EmailProgramID: " + temp);
		bw.append(temp + ",");
		
		// Address
		temp = attributes.get(6);
		logger.trace("Address: " + temp);
		bw.append(temp + ",");
		
		// Action
		temp = attributes.get(7);
		logger.trace("Action: " + temp);
		bw.append(temp + ",");
		
		// Bytes
		temp = attributes.get(8);
		logger.trace("Bytes: " + temp);
		bw.append(temp + ",");
		
		// Attachments
		temp = attributes.get(9);
		logger.trace("Attachments: " + temp);
		bw.append(temp + "\n");
		
		// Hate to have to do it like this - thought I could trust BufferedWriter to handle itself
		// but the output was consistently cutting out at a specific spot without this next line
		bw.flush();
		
		logger.info("Sent one Email Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
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