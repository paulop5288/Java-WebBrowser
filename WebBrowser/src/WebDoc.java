// This file should NOT be assessed
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class WebDoc {
	private String url = null;
	private SortedSet<String> keywords;
	private SortedSet<String> content;
	private SortedSet<String> allWords;
	private String prefix = null;
	private String fileName = null;

	public WebDoc(String url) throws Exception {

		url = url.replaceAll("(%20)+", " ");

		if (url.startsWith("http")) {
			this.prefix = "http:";
		} else if (url.startsWith("file")) {
			this.prefix = "file:";
		}
		int i = url.lastIndexOf("/");
		this.fileName = url.substring(i + 1);
		// search file name
//		if (url.endsWith(".html")) {
//			int i = url.lastIndexOf("/");
//			this.fileName = url.substring(i + 1);
//		} else {
//			throw new Exception("This is not a html file.");
//		}

		this.url = url;
		this.keywords = new TreeSet<String>();
		this.content = new TreeSet<String>();
		this.allWords = new TreeSet<String>();
		try {
			extractWordsFromHTML();
		} catch (Exception e) {
			throw e;
		}

		allWords.addAll(keywords);
		allWords.addAll(content);
		if (this.allWords.isEmpty()) {
			System.err.println("Empty File.");
			throw new Exception("This page is empty.");
		}
	}

	@Override
	public String toString() {

		try {
			return this.prefix + this.fileName + " " + this.content.size()
					+ " (" + this.allWords.first() + "-" + this.allWords.last()
					+ ") " + this.keywords.size();
		} catch (Exception e) {
			System.err.println("Empty Webdoc");
		}
		return "Invalid WebDoc";

	}

	// accessors
	public String getURL() {
		return url;
	}

	public Set<String> getContent() {
		return Collections.unmodifiableSet(content);
	}

	public Set<String> getKeywords() {
		return Collections.unmodifiableSet(keywords);
	}

	public SortedSet<String> getAllWords() {
		return allWords;
	}

	public void printContentsWords() {
		for (String word : content) {
			System.out.println(word);
		}
		System.out.println(content.size());
	}

	public void printKeywords() {
		for (String keyword : keywords) {
			System.out.println(keyword);
		}
		System.out.println(keywords.size());
	}

	public void printAllwords() {
		for (String word : allWords) {
			System.out.println(word);
		}
	}

	// ----------------------------------------------------------------------------
	// private methods

	private void extractWordsFromHTML() throws Exception {
		BufferedReader inputReader = null;
		URL webURL = null;

		try {
			webURL = new URL(this.url);
			inputReader = new BufferedReader(new InputStreamReader(webURL.openStream()));
			extractWordsFromBufferedReader(inputReader);
		} catch (MalformedURLException e) {
			System.err.println("This is not a correct URL.");
			throw e;
		} catch (FileNotFoundException e2) {
			System.err.println("file not found.");
			throw e2;
		} catch (IOException e3) {
			System.err.println("IO exception.");
			throw e3;
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
				extractWordsFromString(currentLine, this.content);
				while (inputReader.ready()) {
					if (currentLine.contains("</title>")) {
						break;
					}
					extractWordsFromString(inputReader.readLine(), this.content);
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
			extractWordsFromString(inputReader.readLine(), this.content);
		}
	}

	private void extractWordsFromString(String currentLine,
			Set<String> resultSet) {
		currentLine = currentLine.trim().replaceAll("<[^<>]+>", "");
		currentLine = currentLine.replaceAll("[^a-zA-Z ]+", "");

		String[] words = currentLine.split(" ");
		for (String word : words) {
			if (word.compareTo("") != 0) {
				resultSet.add(word.trim());
			}
		}
	}

	private void extractKeywordsFromString(String currentLine,
			Set<String> resultSet) {
		String tempString = currentLine.trim().replaceAll(".*content=\"", "");
		tempString = tempString.replaceAll("name=\"keywords\"", "");
		tempString = tempString.replaceAll("[<>\"]", "");
		String[] keywords = tempString.split(",");
		for (String keyword : keywords) {
			resultSet.add(keyword.trim());
		}
	}
}
