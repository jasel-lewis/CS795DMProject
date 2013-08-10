package com.jasel.classes.cs795dm.project;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Class to support a login InstanceType
 */
public class LoginARFFBuilder extends ARFFBuilder {
	private static Logger logger = LogManager.getLogger(LoginARFFBuilder.class);
	
	public LoginARFFBuilder(String filename, String instanceTypeRange,
			String userIDRange, String hostMachineIDRange) throws IOException {
		super(filename, instanceTypeRange, userIDRange, hostMachineIDRange);
		
		writeHeaders();
	}
	
	
	
	@Override
	protected void writeARFFHeaderCustom() throws IOException {
		bw.append("@attribute EventDate date yyMMdd\n");
		bw.append("@attribute EventDay {mon,tue,wed,thu,fri,sat,sun}\n");
		bw.append("@attribute LoginDateTime date yyMMddHHmmss\n");
		bw.append("@attribute LogoutDateTime date yyMMddHHmmss\n");
		bw.append("@attribute SessionDuration date HHmmss\n");
		bw.append("@attribute AvgUserProcesses numeric\n");
		bw.append("@attribute MaxUserProcesses numeric\n");
		bw.append("@attribute CharsTyped numeric\n");
		bw.append("@attribute CPUTime numeric\n");
		bw.flush();
	}
	
	
	
	@Override
	public void addDataInstance(String instance) throws IOException, ParseException {
		String temp = "";
		String eventDate = "";
		String loginDateTime = "";
		String logoutDateTime = "";
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
		
		// LoginDateTime
		loginDateTime = eventDate + attributes.get(4);
		logger.trace("LoginDateTime: " + loginDateTime);
		bw.append(loginDateTime + ",");
		
		// LogoutDateTime
		logoutDateTime = eventDate + attributes.get(5);
		logger.trace("LogoutDateTime: " + logoutDateTime);
		bw.append(logoutDateTime + ",");
		
		// SessionDuration
		temp = sessionDuration(loginDateTime, logoutDateTime);
		logger.trace("SessionDuration: " + temp);
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
		
		// Hate to have to do it like this - thought I could trust BufferedWriter to
		// handle itself but the output was consistently cutting out at a specific
		// spot without this next line
		bw.flush();
		
		logger.info("Sent one Login Pattern instance to the BufferedWriter for the " +
				"file \"" + filename + "\".");
	}
	
	
	
	/**
	 * Determine the session duration from the passed loginDateTime and
	 * logoutDateTime.  Both parameters are expected to be in the yyMMddHHmmss format.
	 * The return will be in HHmmss format.
	 * @param loginDateTime
	 * @param logoutDateTime
	 * @return
	 */
	private String sessionDuration(String loginDateTime, String logoutDateTime) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyMMddHHmmss")
				.withLocale(Locale.ENGLISH);
		DateTime loginDate = dtf.parseDateTime(loginDateTime);
		DateTime logoutDate = dtf.parseDateTime(logoutDateTime);
		Period period = new Period(loginDate, logoutDate);
		
		PeriodFormatter formatter = new PeriodFormatterBuilder()
			.printZeroAlways().minimumPrintedDigits(2)
			.appendHours().appendMinutes().appendSeconds().toFormatter();
		
		return(formatter.print(period));
	}
}