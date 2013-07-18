package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoginARFFBuilder {
	private static Logger logger = LogManager.getLogger(LoginARFFBuilder.class);
	
	private BufferedWriter bw = null;
	private String filename = null;
	
	public LoginARFFBuilder(String filename, String instanceTypeRange, String userIDRange, String hostMachineIDRange)
			throws IOException {
		this.filename = filename;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
		
		bw.append("@relation logindata\n\n");
		bw.append("@attribute InstanceType " + instanceTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.append("@attribute LoginDateTime date YYMMDDHHmmss\n");
		bw.append("@attribute LogoutDateTime date YYMMDDHHmmss\n");
		bw.append("@attribute AvgUserProcesses numeric\n");
		bw.append("@attribute MaxUserProcesses numeric\n");
		bw.append("@attribute CharsTyped numeric\n");
		bw.append("@attribute CPUTime numeric\n\n");
		bw.append("@data\n");
		
		logger.info("Wrote ARFF header information to \"" + filename + "\"");
	}
	
	
	
	public void addDataInstance(String instance) throws IOException {
		String temp = "";
		String eventDate = "";
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
		
		// EventDate
		eventDate = convertDateFormat(attributes.get(3));
		logger.trace("EventDate (flipped to YYMMDD): " + eventDate);
		
		// LoginDateTime
		temp = attributes.get(4);
		logger.trace("Login Time: " + temp);
		bw.append(eventDate + temp + ",");
		
		// LogoutDateTime
		temp = attributes.get(5);
		logger.trace("Logout Time: " + temp);
		bw.append(eventDate + temp + ",");
		
		// AvgUserProcess
		temp = attributes.get(6);
		logger.trace("AvgUserProcess: " + temp);
		bw.append(temp + ",");
		
		// MaxUserProcess
		temp = attributes.get(7);
		logger.trace("MaxUserProcess: " + temp);
		bw.append(temp + ",");
		
		// CharsTyped
		temp = attributes.get(8);
		logger.trace("CharsTyped: " + temp);
		bw.append(temp + ",");
		
		// CPUTime
		temp = attributes.get(9);
		logger.trace("CPUTime: " + temp);
		bw.append(temp + "\n");
		
		// Hate to have to do it like this - thought I could trust BufferedWriter to handle itself
		// but the output was consistently cutting out at a specific spot without this next line
		bw.flush();
		
		logger.info("Sent one Login Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
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