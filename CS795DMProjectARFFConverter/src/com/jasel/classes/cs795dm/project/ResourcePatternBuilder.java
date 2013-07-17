package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePatternBuilder {
	static Logger logger = LogManager.getLogger(ResourcePatternBuilder.class);
	
	private BufferedWriter bw = null;
	private String filename = null;
	
	public ResourcePatternBuilder(String filename, String recordTypeRange, String userIDRange, String hostMachineIDRange,
			String programIDRange, String fileIDRange, String resourceActionRange, String printerIDRange) throws IOException {
		this.filename = filename;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
		
		bw.append("@relation resourcedata\n\n");
		bw.append("@attribute RecordType " + recordTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.append("@attribute StartDate/Time date MMDDYYHHmmss\n");
		bw.append("@attribute ProgramID " + programIDRange + "\n");
		bw.append("@attribute ExecutionTime numeric\n");
		bw.append("@attribute FileID " + fileIDRange + "\n");
		bw.append("@attribute Action " + resourceActionRange + "\n");
		bw.append("@attribute PrinterID " + printerIDRange + "\n");
		bw.append("@attribute Pages numeric\n\n");
		bw.append("@data\n");
		
		logger.info("Wrote ARFF header information to \"" + filename + "\"");
	}
	
	
	
	public void addDataInstance(String instance) throws IOException {
		String temp = "";
		List<String> attributes = Arrays.asList(instance.split(","));
		Iterator<String> iterator = attributes.iterator();
		
		logger.debug("Instance: " + instance);
		logger.debug("Number of attributes: " + attributes.size());
		
		// RecordType
		temp = iterator.next();
		logger.trace("RecordType: " + temp);
		bw.append(temp + ",");
		
		// UserID
		temp = iterator.next();
		logger.trace("UserID: " + temp);
		bw.append(temp + ",");
		
		// HostMachineID
		temp = iterator.next();
		logger.trace("HostMachineID: " + temp);
		bw.append(temp + ",");
		
		// StartDate
		temp = iterator.next();
		logger.trace("StartDate: " + temp);
		bw.append(temp + ",");
		
		// StartTime
		temp = iterator.next();
		logger.trace("StartTime: " + temp);
		bw.append(temp);
		
		while (iterator.hasNext()) {
			temp = iterator.next();
			logger.trace("Resource: " + temp);
			
			switch (temp.toUpperCase().charAt(0)) {
				case 'U':
					// ProgramID (User)
					bw.append("," + temp);
					
					// (UserProgram) ExecutionTime
					temp = iterator.next();
					logger.trace("(UserProgram) ExecutionTime: " + temp);
					bw.append("," + temp);
					break;
				case 'L':
					// ProgramID (Library)
					bw.append("," + temp);
					
					// (LibraryProgram) ExecutionTime
					temp = iterator.next();
					logger.trace("(LibraryProgram) ExecutionTime: " + temp);
					bw.append("," + temp);
					break;
				case 'F':
					// FileID
					bw.append("," + temp);
					
					// (File) Action
					temp = iterator.next();
					logger.trace("(File) Action: " + temp);
					bw.append("," + temp);
					break;
				case 'P':
					// PrinterID
					bw.append("," + temp);
					
					// (Printer) Pages
					temp = iterator.next();
					logger.trace("(Printer) Pages: " + temp);
					bw.append("," + temp);
					break;
				default:
					logger.fatal("Resource expected in cell - no resource found");
					break;
			}
		}
		
		bw.append("\n");
		
		logger.info("Sent one Resource Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
}