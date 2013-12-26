package gcdc2013.diyproperty.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.common.io.Files;

public class JsonFileGenerator {
	
	private static final String STREETS_FILENAME = "streets";
	private static final String PROPERTY_TYPES_FILENAME = "propertytypes";

	/**
	 * @param args
	 *            [0] - Path of the input file e.g.
	 *            "C:\\Beng\\jsonfilegenerator\\streets.txt"
	 * @param args
	 *            [1] - Path of the output file e.g.
	 *            "C:\\Beng\\jsonfilegenerator\\streets.json"
	 */
	public static void main(String[] args) {

		
		String filename = Files.getNameWithoutExtension(args[0]);
		System.out.println("Processing filename : " + filename);
		
		if (filename.equalsIgnoreCase(STREETS_FILENAME)) {
			
			streets(args[0], args[1]);
			
		} else if (filename.equalsIgnoreCase(PROPERTY_TYPES_FILENAME)) {
			
			propertyTypes(args[0], args[1]);
			
		} else {
			System.out.println("Invalid filename! Do nothing");
		}

	}
	
	private static void propertyTypes(String inFilePath, String outFilePath) {
		File outFile = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		String[] tokens = null;

		try {

			outFile = new File(outFilePath);
			// Overwrite previous file, if any
			outFile.createNewFile();

			fw = new FileWriter(outFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);

			bw.write("[");

			br = new BufferedReader(new FileReader(inFilePath));

			String currLine = br.readLine();
			int lineCount = 0;

			// First datum, no comma prepended
			if (currLine != null) {
				tokens = currLine.split(",");
				bw.write("{\"value\":\"" + tokens[1] + "\",\"label\":\"" + tokens[0] + "\"}");
				lineCount++;
			}

			// All subsequent datums, prepend comma
			while ((currLine = br.readLine()) != null) {
				tokens = currLine.split(",");
				bw.write(",{\"value\":\"" + tokens[1] + "\",\"label\":\"" + tokens[0] + "\"}");
				lineCount++;
			}

			bw.write("]");

			System.out.println(lineCount
					+ " property types written to file successfully");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}

	private static void streets(String inFilePath, String outFilePath) {
		File outFile = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		BufferedReader br = null;

		try {

			outFile = new File(outFilePath);
			// Overwrite previous file, if any
			outFile.createNewFile();

			fw = new FileWriter(outFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);

			bw.write("[");

			br = new BufferedReader(new FileReader(inFilePath));

			String currLine = br.readLine();
			int lineCount = 0;

			// First datum, no comma prepended
			if (currLine != null) {
				bw.write("{\"value\":\"" + currLine + "\"}");
				lineCount++;
			}

			// All subsequent datums, prepend comma
			while ((currLine = br.readLine()) != null) {
				bw.write(",{\"value\":\"" + currLine + "\"}");
				lineCount++;
			}

			bw.write("]");

			System.out.println(lineCount
					+ " streets written to file successfully");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}

}
