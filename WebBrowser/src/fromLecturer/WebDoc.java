package fromLecturer;
// This file comes from Stage 1

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class WebDoc {

	private String url;
	private SortedSet<String> words;
	private SortedSet<String> keywords;
	
	public WebDoc(String the_url) {
		url=the_url;
		words = new TreeSet<String>();
		keywords = new TreeSet<String>();
		populate();
	}

	//this is a convenience to create a webdoc very quickly indeed.
	public static WebDoc
	getTestWebDoc(String the_url, String[] the_content_words, String[] the_keywords) {
		WebDoc result = new WebDoc(the_url);
		for (String word: the_content_words) {
			result.words.add(word);
		}
		for (String word: the_keywords) {
			result.keywords.add(word);
		}
		return result;
	}
	
	public String getUrl() {
		return url;
	}

	public Set<String> getContent() {
		return Collections.unmodifiableSet(words);
	}

	public Set<String> getKeywords() {
		return Collections.unmodifiableSet(keywords);
	}
	
	public String
	toString() {
		StringBuffer buffer = new StringBuffer("file:");
		buffer.append(url);
		buffer.append(" ");
		buffer.append(words.size());
		buffer.append(" (");
		buffer.append(words.first());
		buffer.append("-");
		buffer.append(words.last());
		buffer.append(") ");
		buffer.append(keywords.size());
		return buffer.toString();
		
	}
	
	//equals and hashcode - we could also check the contents of the sets, but to be honest
	//the chances of the webpages changing whislt we're indexing probably makes that unneccessary.
	
	public boolean
	equals(Object o) {
		boolean result=false;
		if (o!=null && o instanceof WebDoc) {
			result=url.equals(((WebDoc)o).url);
		}
		return result;
	}
	
	public int
	hashCode() {
		return url.hashCode();
	}
	
	//This is what you would need to implement and call for real if you were actually parsing.
	public void
	populate() {
		
	}
	
}
