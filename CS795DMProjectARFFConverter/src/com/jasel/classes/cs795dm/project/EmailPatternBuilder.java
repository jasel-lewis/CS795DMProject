package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class EmailPatternBuilder {
	private static final String filename = "emaildata.arff";
	private BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
	
	public EmailPatternBuilder(String recordTypeRange, String userIDRange, String hostMachineIDRange,
			String emailProgramIDRange, String emailActionRange) throws IOException {
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
			bw.append(temp + cell.getStringCellValue() + ",");  // Start Date/Time
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // EmailProgramID
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // Address
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // Action
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // Bytes
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue());  // Attachments
		}
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
	}
}