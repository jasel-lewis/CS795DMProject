package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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
	
	
	
	private void writeARFFHeaderHeader() throws IOException {
		bw.append("@relation logindata\n\n");
		bw.append("@attribute InstanceType " + instanceTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
	}
	
	
	
	protected abstract void writeARFFHeaderCustom() throws IOException;
	
	
	
	private void writeARFFHeaderFooter() throws IOException {
		bw.append("\n@data\n");
	}
	
	
	
	protected abstract void addDataInstance(String instance) throws IOException;
	
	
	
	protected void commit() throws IOException {
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
	protected String convertDateFormat(String date) {
		return (date.substring(4) + date.substring(0, 4));
	}
}