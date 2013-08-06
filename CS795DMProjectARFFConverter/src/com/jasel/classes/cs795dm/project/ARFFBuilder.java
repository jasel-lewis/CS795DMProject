package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
	}
	
	
	
	/**
	 * Perform InstanceType-specific functionality and output to the appropriate
	 * ARFF file
	 * @param instance
	 * @throws IOException
	 */
	protected abstract void addDataInstance(String instance) throws IOException;
	
	
	
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
}