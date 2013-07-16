package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class LoginPatternBuilder {
	private static final String filename = "logindata.arff";
	private BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
	
	public LoginPatternBuilder(String recordTypeRange, String userIDRange, String hostMachineIDRange) throws IOException {
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
			bw.append(cell.getStringCellValue());  // CPUTime
		}
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
	}
}