// This file should NOT be assessed
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//This is a sample web index class.
//It maintains a map of words to documents that contain those words.

public class WebIndex {
	private Set<WebDoc> documents;
	private Map<String, Set<WebDoc>> index;

	public WebIndex() {
		documents = new HashSet<WebDoc>();
		index = new HashMap<String, Set<WebDoc>>();
	}

	public void add(WebDoc the_doc) {
		Set<String> words;

		words = the_doc.getAllWords();

		if (words.isEmpty() == false) {
			documents.add(the_doc);
			for (String word : words) {
				if (index.containsKey(word)) {
					index.get(word).add(the_doc);
				} else {
					Set<WebDoc> docs = new HashSet<WebDoc>();
					docs.add(the_doc);
					index.put(word, docs);
				}
			}
		}
	}

	public Set<WebDoc> getAllDocuments() {
		return Collections.unmodifiableSet(documents);
	}

	public Set<WebDoc> matches(String wd) {
		Set<WebDoc> result = index.get(wd);
		if (result == null) {
			result = Collections.emptySet();
		}
		return Collections.unmodifiableSet(result);
	}

	public String toString() {
		StringBuffer result = new StringBuffer("webindex over ");
		result.append(" contains ");
		result.append(index.keySet().size());
		result.append(" words from ");
		result.append(documents.size());
		result.append(" documents");
		return result.toString();
	}

}