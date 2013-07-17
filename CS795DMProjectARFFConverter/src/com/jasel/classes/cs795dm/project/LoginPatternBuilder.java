package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class LoginPatternBuilder {
	private static Logger logger = LogManager.getLogger(LoginPatternBuilder.class);
	
	private BufferedWriter bw = null;
	private String filename = null;
	
	public LoginPatternBuilder(String filename, String recordTypeRange, String userIDRange, String hostMachineIDRange)
			throws IOException {
		this.filename = filename;
		
		bw = new BufferedWriter(new FileWriter(filename));
		
		logger.info("Opened the file \"" + filename + "\" for writing.");
		
		bw.append("@relation logindata\n\n");
		bw.append("@attribute RecordType " + recordTypeRange + "\n");
		bw.append("@attribute UserID " + userIDRange + "\n");
		bw.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		bw.append("@attribute LoginDate/Time date MMDDYYHHmmss\n");
		bw.append("@attribute LogoutDate/Time date MMDDYYHHmmss\n");
		bw.append("@attribute AvgUserProcesses numeric\n");
		bw.append("@attribute MaxUserProcesses numeric\n");
		bw.append("@attribute CharsTyped numeric\n");
		bw.append("@attribute CPUTime numeric\n\n");
		bw.append("@data\n");
		
		logger.info("Wrote ARFF header information to \"" + filename + "\"");
	}
	
	
	
	public void addDataInstance(Iterator<Cell> cellIterator) throws IOException {
		XSSFCell cell = null;
		int temp = 0;
		
		bw.append("1,");  // RecordType
		cell = (XSSFCell)cellIterator.next();
		logger.trace("UserID cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // UserID
		cell = (XSSFCell)cellIterator.next();
		logger.trace("HostMachineID cell raw value: " + cell.getRawValue());
		bw.append(cell.getStringCellValue() + ",");  // HostMachineID
		cell = (XSSFCell)cellIterator.next();
		logger.trace("EventDate cell raw value: " + cell.getRawValue());
		temp = (int)cell.getNumericCellValue();
		cell = (XSSFCell)cellIterator.next();
		logger.trace("LoginTime cell raw value: " + cell.getRawValue());
		bw.append(temp + (int)cell.getNumericCellValue() + ",");  // Login Date/Time
		cell = (XSSFCell)cellIterator.next();
		logger.trace("LogoutTime cell raw value: " + cell.getRawValue());
		bw.append(temp + (int)cell.getNumericCellValue() + ",");  // Logout Date/Time
		cell = (XSSFCell)cellIterator.next();
		logger.trace("AvgUserProcess cell raw value: " + cell.getRawValue());
		bw.append((int)cell.getNumericCellValue() + ",");  // AvgUserProcess
		cell = (XSSFCell)cellIterator.next();
		logger.trace("MaxUserProcess cell raw value: " + cell.getRawValue());
		bw.append((int)cell.getNumericCellValue() + ",");  // MaxUserProcess
		cell = (XSSFCell)cellIterator.next();
		logger.trace("CharsTyped cell raw value: " + cell.getRawValue());
		bw.append((int)cell.getNumericCellValue() + ",");  // CharsTyped
		cell = (XSSFCell)cellIterator.next();
		logger.trace("CPUTime cell raw value: " + cell.getRawValue());
		bw.append((int)cell.getNumericCellValue() + "\n");  // CPUTime
		
		logger.info("Sent one Login Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
}