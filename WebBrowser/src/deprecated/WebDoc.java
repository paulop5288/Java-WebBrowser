package deprecated;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class WebDoc {
	private String url = null;
	private HashMap<String, Integer> keywords;
	private HashMap<String, Integer> contentWords;
	private LinkedList<String> orderedWords = new LinkedList<String>();
	private String prefix = null;
	private String fileName = null;

	public WebDoc(String url) throws Exception {

		url = url.toLowerCase().replaceAll("(%20)+", " ");

		if (url.startsWith("http")) {
			this.prefix = "http:";
		} else if (url.startsWith("file")) {
			this.prefix = "file:";
		}

		// search file name
		if (url.endsWith(".html")) {
			int i = url.lastIndexOf("/");
			this.fileName = url.substring(i + 1);
		}

		this.url = url;
		this.keywords = new HashMap<String, Integer>();
		this.contentWords = new HashMap<String, Integer>();

		try {
			extractWordsFromHTML();
		} catch (Exception e) {
			throw e;
		}

		this.orderedWords.addAll(this.contentWords.keySet());
		Collections.sort(this.orderedWords);

		if (this.orderedWords.isEmpty()) {
			System.err.println("Empty File.");
			throw new Exception();
		}

	}
	
	@Override
	public String toString() {

		try {
			return this.prefix + this.fileName + " " + this.contentWords.size()
					+ " (" + this.orderedWords.getFirst() + "-"
					+ this.orderedWords.getLast() + ") " + this.keywords.size()
					+ " maybe";
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Empty Webdoc");
		}
		return "Invalid WebDoc";

	}
	

	// accessors
	public String getURL() {
		return url;
	}

	public HashMap<String, Integer> getSearchMapForContents() {
		return this.contentWords;
	}

	public HashMap<String, Integer> getSearchMapForKeywords() {
		return this.keywords;
	}

	public LinkedList<String> getContentWords() {
		return this.orderedWords;
	}

	public LinkedList<String> getKeywords() {
		LinkedList<String> orderedKeywords = new LinkedList<String>();
		orderedKeywords.addAll(this.keywords.keySet());
		return orderedKeywords;
	}

	public void printContentsWords() {

		for (String word : orderedWords) {
			System.out.println(word);
		}
	}

	public void printKeywords() {
		Set<String> keywords = this.keywords.keySet();
		for (String keyword : keywords) {
			System.out.println(keyword);
		}
	}



	// ----------------------------------------------------------------------------
	// private methods

	private void extractWordsFromHTML() throws Exception {
		BufferedReader inputReader = null;
		try {
			URL webURL = new URL(this.url);
			inputReader = new BufferedReader(new InputStreamReader(
					webURL.openStream()));

			extractWordsFromBufferedReader(inputReader);
		} catch (MalformedURLException e) {
			System.err.println("This is not a correct URL.");
			throw e;
		} catch (FileNotFoundException e) {
			System.err.println("file not found.");
			throw e;
		} catch (IOException e) {
			System.err.println("IO exception.");
			throw e;
		} finally {
			try {
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (IOException e2) {
				System.out.println("Fail to close file.");
			}
		}
	}

	private void extractWordsFromBufferedReader(BufferedReader inputReader)
			throws IOException {

		// read title
		while (inputReader.ready()) {
			String currentLine = inputReader.readLine();
			if (currentLine.contains("<title>")) {
				extractWordsFromString(currentLine, this.contentWords);
				while (inputReader.ready()) {
					if (currentLine.contains("</title>")) {
						break;
					}
					extractWordsFromString(inputReader.readLine(),
							this.contentWords);
				}
				break;
			}
		}

		// read keywords
		while (inputReader.ready()) {
			String currentLine = inputReader.readLine().toLowerCase();
			if (currentLine.contains("<meta")) {
				extractKeywordsFromString(currentLine, keywords);
				while (inputReader.ready()) {
					if (currentLine.contains(">")) {
						break;
					}
					extractKeywordsFromString(inputReader.readLine(),
							this.keywords);
				}
				break;
			}
			if (currentLine.contains("</head>")) {
				break;
			}
		}

		// read body
		while (inputReader.ready()) {
			extractWordsFromString(inputReader.readLine(), this.contentWords);
		}
	}

	private void extractWordsFromString(String currentLine,
			HashMap<String, Integer> resultMap) {
		currentLine = currentLine.trim().replaceAll("<[^<>]+>", "");
		currentLine = currentLine.replaceAll("[^a-zA-Z ]+", "");

		String[] words = currentLine.split(" ");
		for (String word : words) {
			if (word.compareTo("") != 0) {
				resultMap.put(word.trim(), 1);
			}
		}
	}

	private void extractKeywordsFromString(String currentLine,
			HashMap<String, Integer> resultMap) {
		String tempString = currentLine.trim().replaceAll(".*content=\"", "");
		tempString = tempString.replaceAll("name=\"keywords\"", "");
		tempString = tempString.replaceAll("[<>\"]", "");
		String[] keywords = tempString.split(",");
		for (String keyword : keywords) {
			resultMap.put(keyword.trim(), 1);
		}
	}
}
