package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ResourcePatternBuilder {
	private BufferedWriter bw = null;
	
	public ResourcePatternBuilder(String filename, String recordTypeRange, String userIDRange, String hostMachineIDRange,
			String programIDRange, String fileIDRange, String resourceActionRange, String printerIDRange) throws IOException {
		bw = new BufferedWriter(new FileWriter(filename));
		
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
			bw.append(cell.getStringCellValue() + ",");  // ProgramID
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // ExecutionTime
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // FileID
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // Action
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue() + ",");  // PrinterID
			cell = (XSSFCell)cellIterator.next();
			bw.append(cell.getStringCellValue());  // Pages
		}
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
	}
}