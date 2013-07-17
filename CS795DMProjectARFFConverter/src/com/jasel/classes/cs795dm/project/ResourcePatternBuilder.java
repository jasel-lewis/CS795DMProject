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
		String content = "";
		
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
		
		while (cellIterator.hasNext()) {
			cell = (XSSFCell)cellIterator.next();
			logger.trace("First resource cell raw value: " + cell.getRawValue());
			content = cell.getStringCellValue();
			
			switch (content.toUpperCase().charAt(0)) {
				case 'U': case 'L':
					bw.append(content + ",");  // ProgramID
					cell = (XSSFCell)cellIterator.next();
					logger.trace("(UserProgram) ExecutionTime cell raw value: " + cell.getRawValue());
					bw.append(cell.getStringCellValue() + ",");  // ExecutionTime
					break;
				case 'F':
					bw.append(content + ",");  // FileID
					cell = (XSSFCell)cellIterator.next();
					logger.trace("(File) Action cell raw value: " + cell.getRawValue());
					bw.append(cell.getStringCellValue() + ",");  // Action
					break;
				case 'P':
					bw.append(content + ",");  // PrinterID
					cell = (XSSFCell)cellIterator.next();
					logger.trace("(Printer) Pages cell raw value: " + cell.getRawValue());
					bw.append(cell.getStringCellValue() + "\n");  // Pages
					break;
				default:
					logger.fatal("Resource expected in cell - no resource found/");
					break;
			}
		}
		
		logger.info("Sent one Resource Pattern instance to the BufferedWriter for the file \"" + filename + "\".");
	}
	
	
	
	public void commit() throws IOException {
		bw.close();
		logger.info("Closed the file \"" + filename + "\".");
	}
}