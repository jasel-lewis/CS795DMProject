package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;

public class EmailPatternBuilder {
	private static final String filename = "emaildata.arff";
	private StringBuffer sb = new StringBuffer();
	
	public EmailPatternBuilder(String recordTypeRange, String userIDRange, String hostMachineIDRange,
			String emailProgramIDRange, String emailActionRange) {
		sb.append("@relation emaildata\n\n");
		sb.append("@attribute RecordType " + recordTypeRange + "\n");
		sb.append("@attribute UserID " + userIDRange + "\n");
		sb.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		sb.append("@attribute StartDate/Time date MMDDYYHHmmss\n");
		sb.append("@attribute EmailProgramID " + emailProgramIDRange + "\n");
		sb.append("@attribute Address string\n");
		sb.append("@attribute Action " + emailActionRange + "\n");
		sb.append("@attribute Bytes numeric\n");
		sb.append("@attribute Attachments numeric\n\n");
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