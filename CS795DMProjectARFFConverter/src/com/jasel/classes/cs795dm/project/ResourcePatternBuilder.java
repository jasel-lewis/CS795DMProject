package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

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
	
	
	
	public void addDataInstance(Iterator<Cell> cellIterator) throws IOException {
		XSSFCell cell = null;
		int temp = 0;
		String content = "";
		
		bw.append("1,");  // RecordType
		cell = (XSSFCell)cellIterator.next();
		bw.append(cell.getStringCellValue() + ",");  // UserID
		cell = (XSSFCell)cellIterator.next();
		bw.append(cell.getStringCellValue() + ",");  // HostMachineID
		cell = (XSSFCell)cellIterator.next();
		temp = (int)cell.getNumericCellValue();
		cell = (XSSFCell)cellIterator.next();
		bw.append(temp + (int)cell.getNumericCellValue() + ",");  // Start Date/Time
		
		while (cellIterator.hasNext()) {
			cell = (XSSFCell)cellIterator.next();
			content = cell.getStringCellValue();
			
			switch (content.toUpperCase().charAt(0)) {
				case 'U':
					bw.append(content + ",");  // ProgramID
					cell = (XSSFCell)cellIterator.next();
					bw.append((int)cell.getNumericCellValue() + ",");  // ExecutionTime
				case 'L':
					bw.append(content + ",");  // ProgramID
					cell = (XSSFCell)cellIterator.next();
					bw.append((int)cell.getNumericCellValue() + ",");  // ExecutionTime
				case 'F':
					bw.append(content + ",");  // FileID
					cell = (XSSFCell)cellIterator.next();
					bw.append(cell.getStringCellValue() + ",");  // Action
				case 'P':
					bw.append(content + ",");  // PrinterID
					cell = (XSSFCell)cellIterator.next();
					bw.append((int)cell.getNumericCellValue() + "\n");  // Pages
				default:
					logger.fatal("Resource expected in cell - no resource found/");
			}
		}
		
		logger.info("Sent one Resource Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
}