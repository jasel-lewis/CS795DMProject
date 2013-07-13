package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;

public class ResourcePatternBuilder {
	private static final String filename = "resourcedata.arff";
	private StringBuffer sb = new StringBuffer();
	
	public ResourcePatternBuilder(String recordTypeRange, String userIDRange, String hostMachineIDRange,
			String programIDRange, String fileIDRange, String resourceActionRange, String printerIDRange) {
		sb.append("@relation resourcedata\n\n");
		sb.append("@attribute RecordType " + recordTypeRange + "\n");
		sb.append("@attribute UserID " + userIDRange + "\n");
		sb.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		sb.append("@attribute StartDate/Time date MMDDYYHHmmss\n");
		sb.append("@attribute ProgramID " + programIDRange + "\n");
		sb.append("@attribute ExecutionTime numeric\n");
		sb.append("@attribute FileID " + fileIDRange + "\n");
		sb.append("@attribute Action " + resourceActionRange + "\n");
		sb.append("@attribute PrinterID " + printerIDRange + "\n");
		sb.append("@attribute Pages numeric\n\n");
		sb.append("@data\n");
	}
	
	
	
	public void addDataInstance(XSSFRow row) {
		;
	}
	
	
	
	public void commit() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		bw.write(sb.toString());
		bw.close();
	}
}