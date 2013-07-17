package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoginPatternBuilder {
	private static Logger logger = LogManager.getLogger(LoginPatternBuilder.class);
	
	private BufferedWriter bw = null;
	private String filename = null;
	
	public LoginPatternBuilder(String filename, String recordTypeRange, String userIDRange, String hostMachineIDRange)
			throws IOException {
		this.filename = filename;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
		
		bw.append("@relation logindata\n\n");
		bw.append("@attribute RecordType " + recordTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.append("@attribute LoginDate/Time date MMDDYYHHmmss\n");
		bw.append("@attribute LogoutDate/Time date MMDDYYHHmmss\n");
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
		
		// RecordType
		temp = attributes.get(0);
		logger.trace("RecordType: " + temp);
		bw.append(temp + ",");
		
		// UserID
		temp = attributes.get(1);
		logger.trace("UserID: " + temp);
		bw.append(temp + ",");
		
		// HostMachineID
		temp = attributes.get(2);
		logger.trace("HostMachineID: " + temp);
		bw.append(temp + ",");
		
		// Event Date
		eventDate = attributes.get(3);
		logger.trace("Event Date: " + eventDate);
		
		// Login Date/Time
		temp = attributes.get(4);
		logger.trace("Login Time: " + temp);
		bw.append(eventDate + temp + ",");
		
		// Logout Date/Time
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
		
		logger.info("Sent one Login Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
}