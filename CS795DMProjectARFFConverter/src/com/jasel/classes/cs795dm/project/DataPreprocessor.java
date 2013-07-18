/**
 * 
 */
package com.jasel.classes.cs795dm.project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author jasel
 *
 */
public class DataPreprocessor {
	private final static String INPUT_FILENAME = "projectdata.csv";
	private final static String LOGIN_OUTPUT_FILENAME = "logindata.arff";
	private final static String RESOURCE_OUTPUT_FILENAME = "resourcedata.arff";
	private final static String EMAIL_OUTPUT_FILENAME = "emaildata.arff";
	
	private static Logger logger = LogManager.getLogger(DataPreprocessor.class);
	
	private static StringBuilder sbInstanceTypeRange = new StringBuilder("{1");
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
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_FILENAME)));
		
		String line = "";
		int lineCount = 1;
		int loginPatternCount = 1;
		int resourcePatternCount = 1;
		int emailPatternCount = 1;
		
		generateValueRanges();
		
		loginBuilder = new LoginPatternBuilder(LOGIN_OUTPUT_FILENAME, sbInstanceTypeRange.toString(),
				sbUserIDRange.toString(), sbHostMachineIDRange.toString());
		resourceBuilder = new ResourcePatternBuilder(RESOURCE_OUTPUT_FILENAME, sbInstanceTypeRange.toString(),
				sbUserIDRange.toString(), sbHostMachineIDRange.toString(), sbProgramIDRange.toString(),
				sbFileIDRange.toString(), resourceActionRange, sbPrinterIDRange.toString());
		emailBuilder = new EmailPatternBuilder(EMAIL_OUTPUT_FILENAME, sbInstanceTypeRange.toString(),
				sbUserIDRange.toString(), sbHostMachineIDRange.toString(), sbEmailProgramIDRange.toString(),
				emailActionRange);
		
		while ((line = br.readLine()) != null) {
			logger.trace("** Line " + lineCount++);
			
			// For some reason, Excel is appending a bunch of empty commas as the end of each line
			// when converting to CSV.  Get rid of them.
			line = line.trim().replaceAll(",*$", "");
			
			switch (Integer.parseInt(line.substring(0, 1))) {
				case InstanceType.LOGIN:
					logger.info("Recognized a Login Pattern (# " + loginPatternCount++ + ") - sending to LoginPatternBuilder.");
					loginBuilder.addDataInstance(line);
					break;
				case InstanceType.RESOURCE:
					logger.info("Recognized a Resource Pattern (# " + resourcePatternCount++ + ") - sending to ResourcPatternBuilder.");
					resourceBuilder.addDataInstance(line);
					break;
				case InstanceType.EMAIL:
					logger.info("Recognized an Email Pattern (# " + emailPatternCount++ + ") - sending to EmailPatternBuilder");
					emailBuilder.addDataInstance(line);
					break;
				default:
					logger.fatal("Instance did not have a valid InstanceType");
					br.close();
					System.exit(1);
					break;
			}
		}
		
		br.close();
	}
	
	
	
	private static void generateValueRanges() {
		// Generate InstanceType value range
		for (int i = 2; i <= 3; i++) {
			sbInstanceTypeRange.append("," + i);
		}
		
		sbInstanceTypeRange.append("}");
		
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