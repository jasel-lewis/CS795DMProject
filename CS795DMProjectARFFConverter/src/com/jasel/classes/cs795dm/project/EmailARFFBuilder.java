package com.jasel.classes.cs795dm.project;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
		bw.append("@attribute StartDateTime date YYMMDDHHmmss\n");
		bw.append("@attribute EmailProgramID " + emailProgramIDRange + "\n");
		bw.append("@attribute Address string\n");
		bw.append("@attribute Action " + emailActionRange + "\n");
		bw.append("@attribute Bytes numeric\n");
		bw.append("@attribute Attachments numeric\n");
	}
	
	
	
	@Override
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
}