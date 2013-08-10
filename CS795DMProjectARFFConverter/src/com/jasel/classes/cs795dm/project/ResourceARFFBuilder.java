package com.jasel.classes.cs795dm.project;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to support a resource InstanceType
 */
public class ResourceARFFBuilder extends ARFFBuilder {
	static Logger logger = LogManager.getLogger(ResourceARFFBuilder.class);
	
	private String programIDRange = null;
	private String fileIDRange = null;
	private String resourceActionRange = null;
	private String printerIDRange = null;
	
	public ResourceARFFBuilder(String filename, String instanceTypeRange, String userIDRange, String hostMachineIDRange,
			String programIDRange, String fileIDRange, String resourceActionRange, String printerIDRange) throws IOException {
		super(filename, instanceTypeRange, userIDRange, hostMachineIDRange);
		
		this.programIDRange = programIDRange;
		this.fileIDRange = fileIDRange;
		this.resourceActionRange = resourceActionRange;
		this.printerIDRange = printerIDRange;
		
		writeHeaders();
	}
	
	
	
	@Override
	protected void writeARFFHeaderCustom() throws IOException {
		bw.append("@attribute EventDate date yyMMdd\n");
		bw.append("@attribute EventDay {mon,tue,wed,thu,fri,sat,sun}\n");
		bw.append("@attribute StartDateTime date yyMMddHHmmss\n");
		bw.append("@attribute ProgramID " + programIDRange + "\n");
		bw.append("@attribute ExecutionTime date HHmmss\n");
		bw.append("@attribute FileID " + fileIDRange + "\n");
		bw.append("@attribute Action " + resourceActionRange + "\n");
		bw.append("@attribute PrinterID " + printerIDRange + "\n");
		bw.append("@attribute Pages numeric\n");
		bw.flush();
	}
	
	
	
	@Override
	public void addDataInstance(String instance) throws IOException, ParseException {
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

		// EventDay
		temp = getDay(eventDate);
		logger.trace("EventDay (yyMMdd): " + temp);
		bw.append(temp + ",");
		
		// StartDateTime
		temp = eventDate + attributes.get(4);
		logger.trace("StartDateTime: " + temp);
		bw.append(temp + ",");
		
		// ProgramID
		temp = attributes.get(5);
		logger.trace("ProgramID: " + temp);
		bw.append(temp + ",");
		
		// ExecutionTime
		temp = attributes.get(6);
		logger.trace("ExecutionTime: " + temp);
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
}