package gcdc2013.diyproperty.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileGenerator {

	/**
	 * @param args
	 *            args[0] - Path of the input file e.g.
	 *            "C:\\Beng\\jsonfilegenerator\\streets.txt" args[1] - Path of
	 *            the output file e.g.
	 *            "C:\\Beng\\jsonfilegenerator\\streets.json"
	 */
	public static void main(String[] args) {
		BufferedReader br = null;
		File outFile = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			outFile = new File(args[1]);
			// Overwrite previous file, if any
			outFile.createNewFile();

			fw = new FileWriter(outFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			
			bw.write("[");

			br = new BufferedReader(new FileReader(args[0]));

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
			
			System.out.println(lineCount + " streets written to file successfully");

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
