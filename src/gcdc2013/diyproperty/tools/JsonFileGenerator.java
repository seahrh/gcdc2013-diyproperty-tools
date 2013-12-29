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
	private static final String FEATURES_FILENAME = "features";
	private static final String FLAT_MODELS_FILENAME = "flatmodels";
	private static final String DEVELOPERS_FILENAME = "developers";

	private static final String[] MANDATORY_TERMS = { "pte ltd", "pte. ltd.",
			"ltd", "(pte) ltd", "(pte.) ltd", "private limited",
			"(private) limited", "(private) ltd", "pte. ltd", "pte ltd." };

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

		if (filename.equalsIgnoreCase(STREETS_FILENAME)
				|| filename.equalsIgnoreCase(FEATURES_FILENAME)) {

			txtToJson(args[0], args[1]);

		} else if (filename.equalsIgnoreCase(PROPERTY_TYPES_FILENAME)
				|| filename.equalsIgnoreCase(FLAT_MODELS_FILENAME)) {

			csvToJson(args[0], args[1]);

		} else if (filename.equalsIgnoreCase(DEVELOPERS_FILENAME)) {

			developersToJson(args[0], args[1]);

		} else {
			System.out.println("Invalid filename! Do nothing");
		}

	}

	private static void csvToJson(String inFilePath, String outFilePath) {
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
				bw.write("{\"value\":\"" + tokens[1] + "\",\"label\":\""
						+ tokens[0] + "\"}");
				lineCount++;
			}

			// All subsequent datums, prepend comma
			while ((currLine = br.readLine()) != null) {
				tokens = currLine.split(",");
				bw.write(",{\"value\":\"" + tokens[1] + "\",\"label\":\""
						+ tokens[0] + "\"}");
				lineCount++;
			}

			bw.write("]");

			System.out.println(lineCount
					+ " items written to file successfully");

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

	private static void txtToJson(String inFilePath, String outFilePath) {
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
					+ " items written to file successfully");

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

	private static void developersToJson(String inFilePath, String outFilePath) {
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
			int validDevelopers = 0;

			// First datum, no comma prepended
			if (currLine != null && isValidDeveloper(currLine)) {
				bw.write("{\"value\":\"" + currLine + "\"}");
				lineCount++;
			}

			// All subsequent datums, prepend comma
			while ((currLine = br.readLine()) != null) {

				if (isValidDeveloper(currLine)) {
					bw.write(",{\"value\":\"" + currLine + "\"}");
					validDevelopers++;
				}

				lineCount++;
			}

			bw.write("]");

			System.out.println(validDevelopers + " out of total " + lineCount
					+ " items are valid and written to file successfully");

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

	private static boolean isValidDeveloper(String name) {

		for (int i = 0; i < MANDATORY_TERMS.length; i++) {

			if (name.toLowerCase().contains(MANDATORY_TERMS[i])) {

				return true;

			}

		}
		return false;
	}

}
