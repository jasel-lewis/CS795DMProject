package com.jasel.classes.cs795dm.project;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to support an email InstanceType
 */
public class EmailARFFBuilder extends ARFFBuilder {
	static Logger logger = LogManager.getLogger(EmailARFFBuilder.class);
	
	private String emailProgramIDRange = null;
	private String emailActionRange = null;
	
	public EmailARFFBuilder(String filename, String instanceTypeRange, String userIDRange, String hostMachineIDRange,
			String emailProgramIDRange, String emailActionRange) throws IOException {
		super(filename, instanceTypeRange, userIDRange, hostMachineIDRange);
		
		this.emailProgramIDRange = emailProgramIDRange;
		this.emailActionRange = emailActionRange;
	}
	
	
	
	@Override
	protected void writeARFFHeaderCustom() throws IOException {
		bw.append("@attribute EventDate date yyMMdd\n");
		bw.append("@attribute EventDay {mon,tue,wed,thu,fri,sat,sun}\n");
		bw.append("@attribute StartDateTime date yyMMddHHmmss\n");
		bw.append("@attribute EmailProgramID " + emailProgramIDRange + "\n");
		bw.append("@attribute Address string\n");
		bw.append("@attribute Action " + emailActionRange + "\n");
		bw.append("@attribute Bytes numeric\n");
		bw.append("@attribute Attachments numeric\n");
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
}