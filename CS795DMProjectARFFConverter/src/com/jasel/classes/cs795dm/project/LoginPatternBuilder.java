package com.jasel.classes.cs795dm.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;

public class LoginPatternBuilder {
	private static final String filename = "logindata.arff";
	private StringBuffer sb = new StringBuffer();
	
	public LoginPatternBuilder(String recordTypeRange, String userIDRange, String hostMachineIDRange) {
		sb.append("@relation logindata\n\n");
		sb.append("@attribute RecordType " + recordTypeRange + "\n");
		sb.append("@attribute UserID " + userIDRange + "\n");
		sb.append("@attribute HostMachineID " + hostMachineIDRange + "\n");
		sb.append("@attribute LoginDate/Time date MMDDYYHHmmss\n");
		sb.append("@attribute LogoutDate/Time date MMDDYYHHmmss\n");
		sb.append("@attribute AvgUserProcesses numeric\n");
		sb.append("@attribute MaxUserProcesses numeric\n");
		sb.append("@attribute CharsTyped numeric\n");
		sb.append("@attribute CPUTime numeric\n\n");
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