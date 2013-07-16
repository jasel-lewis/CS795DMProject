package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class LoginPatternBuilder {
	static Logger logger = Logger.getLogger(LoginPatternBuilder.class);
	
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
		String temp = "";
		
		while (cellIterator.hasNext()) {
			bw.append("1,");  // RecordType
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // UserID
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // HostMachineID
			cell = (XSSFCell)cellIterator.next();
			temp = cell.getStringCellValue();
			cell = (XSSFCell)cellIterator.next();
			bw.append(temp + cell.getStringCellValue() + ",");  // Login Date/Time
			cell = (XSSFCell)cellIterator.next();
			bw.append(temp + cell.getStringCellValue() + ",");  // Logout Date/Time
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // AvgUserProcess
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // MaxUserProcess
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // CharsTyped
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + "\n");  // CPUTime
			
			logger.info("Sent one Login Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
		}
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
}