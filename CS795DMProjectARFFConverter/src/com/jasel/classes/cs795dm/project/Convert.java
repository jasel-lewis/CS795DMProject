/**
 * 
 */
package com.jasel.classes.cs795dm.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author jasel
 *
 */
public class Convert {
	private final static String FILENAME = "projectdata.xlsx";
	private static StringBuilder sbRecordTypeRange = new StringBuilder("{1");
	private static StringBuilder sbUserIDRange = new StringBuilder("{U01");
	private static StringBuilder sbProgramIDRange = new StringBuilder("{UP001");
	private static StringBuilder sbFileIDRange = new StringBuilder("{F0001");
	private static StringBuilder sbPrinterIDRange = new StringBuilder("{PR1");
	private static StringBuilder sbEmailProgramIDRange = new StringBuilder("{E1");
	private static StringBuilder sbHostMachineIDRange = new StringBuilder("{M01");
	private static String resourceActionRange = new String("R,RW,W");
	private static String emailActionRange = new String("S,R");

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		LoginPatternBuilder loginBuilder = null;
		ResourcePatternBuilder resourceBuilder = null;
		EmailPatternBuilder emailBuilder = null;
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		XSSFRow row = null;
		Iterator<Row> rowIterator = null;
		XSSFCell cell = null;
		Iterator<Cell> cellIterator = null;
		
		
		generateValueRanges();
		
		loginBuilder = new LoginPatternBuilder(sbRecordTypeRange.toString(), sbUserIDRange.toString(),
				sbHostMachineIDRange.toString());
		resourceBuilder = new ResourcePatternBuilder(sbRecordTypeRange.toString(), sbUserIDRange.toString(),
				sbHostMachineIDRange.toString(), sbProgramIDRange.toString(), sbFileIDRange.toString(),
				resourceActionRange, sbPrinterIDRange.toString());
		emailBuilder = new EmailPatternBuilder(sbRecordTypeRange.toString(), sbUserIDRange.toString(),
				sbHostMachineIDRange.toString(), sbEmailProgramIDRange.toString(), emailActionRange);
		
		// Create an internal POI XSSF Workbook from the Excel data file
		workbook = new XSSFWorkbook(new FileInputStream(FILENAME));
		
		worksheet = workbook.getSheetAt(0);
		
		rowIterator = worksheet.rowIterator();
		
		while (rowIterator.hasNext()) {
			row = (XSSFRow)rowIterator.next();
			cellIterator = row.cellIterator();
			
			cell = (XSSFCell)cellIterator.next();
			
			switch (Integer.parseInt(cell.getStringCellValue())) {
				case RecordType.LOGIN:
					loginBuilder.addDataInstance(row);
					break;
				case RecordType.RESOURCE:
					resourceBuilder.addDataInstance(row);
					break;
				case RecordType.EMAIL:
					emailBuilder.addDataInstance(row);
					break;
				default:
					break;
			}
		}
	}
	
	
	
	private static void generateValueRanges() {
		// Generate RecordType value range
		for (int i = 2; i <= 3; i++) {
			sbRecordTypeRange.append("," + i);
		}
		
		sbRecordTypeRange.append("}");
		
		// Generate UserID value range
		for (int i = 2; i < 10; i++) {
			sbUserIDRange.append(",U0" + i);
		}
		
		for (int i = 10; i <=20; i++) {
			sbUserIDRange.append(",U" + i);
		}
		
		sbUserIDRange.append("}");
		
		// Generate UserProgramID value range
		for (int i = 2; i < 10; i++) {
			sbProgramIDRange.append(",UP00" + i);
		}
		
		for (int i = 10; i < 100; i++) {
			sbProgramIDRange.append(",UP0" + i);
		}
		
		for (int i = 100; i <= 500; i++) {
			sbProgramIDRange.append(",UP" + i);
		}
		
		sbProgramIDRange.append(",LP001");
		
		// Generate LibraryProgramID value range
		for (int i = 2; i < 10; i++) {
			sbProgramIDRange.append(",LP00" + i);
		}
		
		for (int i = 10; i < 100; i++) {
			sbProgramIDRange.append(",LP0" + i);
		}
		
		sbProgramIDRange.append(",LP100}");
		
		// Generate FileID value range
		for (int i = 2; i < 10; i++) {
			sbFileIDRange.append(",F000" + i);
		}
		
		for (int i = 10; i < 100; i++) {
			sbFileIDRange.append(",F00" + i);
		}
		
		for (int i = 100; i < 1000; i++) {
			sbFileIDRange.append(",F0" + i);
		}
		
		for (int i = 1000; i <= 2000; i++) {
			sbFileIDRange.append(",F" + i);
		}
		
		sbFileIDRange.append("}");
		
		// Generate PrinterID value range
		for (int i = 2; i <= 6; i++) {
			sbPrinterIDRange.append(",PR" + i);
		}
		
		sbPrinterIDRange.append("}");
		
		// Generate EmailProgramID value range
		for (int i = 2; i <= 5; i++) {
			sbEmailProgramIDRange.append(",E" + i);
		}
		
		sbEmailProgramIDRange.append("}");
		
		// Generate HostMachineID value range
		for (int i = 2; i < 10; i++) {
			sbHostMachineIDRange.append(",M0" + i);
		}
		
		for (int i = 10; i <= 30; i++) {
			sbHostMachineIDRange.append(",M" + i);
		}
		
		sbHostMachineIDRange.append("}");
	}
}