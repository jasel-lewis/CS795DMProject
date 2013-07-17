package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class EmailPatternBuilder {
	static Logger logger = LogManager.getLogger(EmailPatternBuilder.class);
	
	private BufferedWriter bw = null;
	private String filename = null;
	
	public EmailPatternBuilder(String filename, String recordTypeRange, String userIDRange, String hostMachineIDRange,
			String emailProgramIDRange, String emailActionRange) throws IOException {
		this.filename = filename;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
		
		bw.append("@relation emaildata\n\n");
		bw.append("@attribute RecordType " + recordTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.append("@attribute StartDate/Time date MMDDYYHHmmss\n");
		bw.append("@attribute EmailProgramID " + emailProgramIDRange + "\n");
		bw.append("@attribute Address string\n");
		bw.append("@attribute Action " + emailActionRange + "\n");
		bw.append("@attribute Bytes numeric\n");
		bw.append("@attribute Attachments numeric\n\n");
		bw.append("@data\n");
		
		logger.info("Wrote ARFF header information to \"" + filename + "\"");
	}
	
	
	
	public void addDataInstance(Iterator<Cell> cellIterator) throws IOException {
		XSSFCell cell = null;
		
		bw.append("1,");  // RecordType
		cell = (XSSFCell)cellIterator.next();
		logger.trace("UserID cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // UserID
		cell = (XSSFCell)cellIterator.next();
		logger.trace("HostMachineID cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // HostMachineID
		cell = (XSSFCell)cellIterator.next();
		logger.trace("StartDate cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue());  // Start Date
		cell = (XSSFCell)cellIterator.next();
		logger.trace("StartTime cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // Start Time
		cell = (XSSFCell)cellIterator.next();
		logger.trace("EmailProgramID cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // EmailProgramID
		cell = (XSSFCell)cellIterator.next();
		logger.trace("Address cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // Address
		cell = (XSSFCell)cellIterator.next();
		logger.trace("Action cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // Action
		cell = (XSSFCell)cellIterator.next();
		logger.trace("Bytes cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // Bytes
		cell = (XSSFCell)cellIterator.next();
		logger.trace("Attachments cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + "\n");  // Attachments
		
		logger.info("Sent one Email Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
}