
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class CommandReader {

	public CommandReader() {
	}

	// read input from command line
	public String readCommandLine(String print) {

		String currentLine = null;
		BufferedReader read = null;
		System.out.print(print);
		try {
			read = new BufferedReader(new InputStreamReader(
					System.in));
			currentLine = read.readLine().toLowerCase();
		} catch (IOException e) {
			currentLine = readCommandLine("Can't read. Please try again.\n")
					.toLowerCase();
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return currentLine;
	}
	
	
	// read input from command line argument
	public ArrayList<String> readFileArgs(String fliePath) {

		ArrayList<String> files = new ArrayList<String>();
		BufferedReader inputReader = null;
		try {
			URL url = new URL("file:\\\\\\" + fliePath);
			inputReader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			
			while (inputReader.ready()) {
				String currentLine = inputReader.readLine();
				if (currentLine.compareToIgnoreCase("") != 0) {
					files.add(currentLine);
				}
			}
		} catch (IOException e) {
			System.err.println("Wrong file name.");
		} finally {
			if (inputReader != null) {
				try {
					inputReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return files;
	}
}
