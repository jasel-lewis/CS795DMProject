package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Class of common methods and variables from which to extend the other
 * ARFF builder classes
 */
public abstract class ARFFBuilder {
	protected static Logger logger = LogManager.getLogger(ARFFBuilder.class);
	
	protected BufferedWriter bw = null;
	protected String filename = null;
	private String instanceTypeRange = null;
	private String userIDRange = null;
	private String hostMachineIDRange = null;
	
	public ARFFBuilder(String filename, String instanceTypeRange, String userIDRange, String hostMachineIDRange)
			throws IOException {
		this.filename = filename;
		this.instanceTypeRange = instanceTypeRange;
		this.userIDRange = userIDRange;
		this.hostMachineIDRange = hostMachineIDRange;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
	}
	
	
	
	protected void writeHeaders() throws IOException {
		writeARFFHeaderHeader();
		writeARFFHeaderCustom();
		writeARFFHeaderFooter();
		
		logger.info("Wrote ARFF header information to \"" + filename + "\"");
	}
	
	
	
	/**
	 * Write the "header" (the common, top portion) of the ARFF header
	 * @throws IOException
	 */
	private void writeARFFHeaderHeader() throws IOException {
		bw.append("@relation logindata\n\n");
		bw.append("@attribute InstanceType " + instanceTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.flush();
	}
	
	
	
	/**
	 * Write the non-common middle portion of the ARFF header
	 * @throws IOException
	 */
	protected abstract void writeARFFHeaderCustom() throws IOException;
	
	
	
	/**
	 * Write the "footer" (common, bottom portion) of the ARFF header
	 * @throws IOException
	 */
	private void writeARFFHeaderFooter() throws IOException {
		bw.append("\n@data\n");
		bw.flush();
	}
	
	
	
	/**
	 * Perform InstanceType-specific functionality and output to the appropriate
	 * ARFF file
	 * @param instance
	 * @throws IOException
	 * @throws ParseException 
	 */
	protected abstract void addDataInstance(String instance) throws IOException, ParseException;
	
	
	
	/**
	 * Conclude output to the ARFF file
	 * @throws IOException
	 */
	protected void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
	
	
	
	/**
	 * The provided data presents a date in the MMddyy format.  We wish to utilize a decreasing
	 * order of chronological granularity - specifically a yyMMdd format.  This method
	 * expects a String representing a date in the MMddyy format and returns the same but
	 * formatted as yyMMdd.
	 * @param date
	 * @return
	 */
	protected String convertDateFormat(String date) {
		return (date.substring(4) + date.substring(0, 4));
	}
	
	
	/**
	 * This method determines what day of the week is represented by the passed date (expected
	 * to be in yyMMdd format).  Returned String will be one of {mon,tue,wed,thu,fri,sat,sun}.
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	protected String getDay(String date) throws ParseException {
		int day = 0;
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(new SimpleDateFormat("yyMMdd").parse(date));
		day = calendar.get(Calendar.DAY_OF_WEEK);
		
		switch (day) {
			case Calendar.MONDAY:
				return "mon";
			case Calendar.TUESDAY:
				return "tue";
			case Calendar.WEDNESDAY:
				return "wed";
			case Calendar.THURSDAY:
				return "thu";
			case Calendar.FRIDAY:
				return "fri";
			case Calendar.SATURDAY:
				return "sat";
			case Calendar.SUNDAY:
				return "sun";
			default:
				return "UNDEF";
		}
	}
}