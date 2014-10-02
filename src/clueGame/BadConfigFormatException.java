package clueGame;

import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {

	BadConfigFormatException(String file) {
		String logFile = "logFile.txt";
		// attempt to write the Exception message to a text file
		try {
			PrintWriter out = new PrintWriter(logFile);
			out.println("ERROR: the configuration file is not valid");
			out.close();
		} catch (IOException e) {
			System.out.println("ERROR: Cannot write to the file: '" + logFile + "'");
		}
		System.out.println("ERROR: the configuration file: " + file + " is not valid");
	}
}
