package com.jasel.classes.cs795dm.project;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Class to support a login InstanceType
 */
public class LoginARFFBuilder extends ARFFBuilder {
	private static Logger logger = LogManager.getLogger(LoginARFFBuilder.class);
	
	public LoginARFFBuilder(String filename, String instanceTypeRange, String userIDRange, String hostMachineIDRange)
			throws IOException {
		super(filename, instanceTypeRange, userIDRange, hostMachineIDRange);
	}
	
	
	
	@Override
	protected void writeARFFHeaderCustom() throws IOException {
		bw.append("@attribute EventDate date yyMMdd\n");
		bw.append("@attribute LoginTime date HHmmss\n");
		bw.append("@attribute LogoutTime date HHmmss\n");
		bw.append("@attribute AvgUserProcesses numeric\n");
		bw.append("@attribute MaxUserProcesses numeric\n");
		bw.append("@attribute CharsTyped numeric\n");
		bw.append("@attribute CPUTime numeric\n");
	}
	
	
	
	@Override
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
		logger.trace("EventDate (flipped to yyMMdd): " + eventDate);
		bw.append(eventDate + ",");
		
		// LoginDateTime
		temp = attributes.get(4);
		logger.trace("Login Time: " + temp);
		bw.append(temp + ",");
		
		// LogoutDateTime
		temp = attributes.get(5);
		logger.trace("Logout Time: " + temp);
		bw.append(temp + ",");
		
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
}