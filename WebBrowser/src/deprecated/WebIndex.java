package deprecated;
import WebDoc;

import java.util.ArrayList;

public class WebIndex {
	private boolean indexByContents = true;
	private ArrayList<WebDoc> webDocs = null;

	public WebIndex(boolean indexByContents) {
		this.webDocs = new ArrayList<WebDoc>();
		this.indexByContents = indexByContents;
	}

	public void add(WebDoc doc) {
		this.webDocs.add(doc);
	}

	public ArrayList<WebDoc> getAllDocuments() {
		return this.webDocs;
	}

	public ArrayList<WebDoc> getMatches(String wd) {
		ArrayList<WebDoc> matchedDocs = new ArrayList<WebDoc>();
		for (WebDoc webDoc : this.webDocs) {
			if (webDoc.getSearchMapForContents().containsKey(wd)
					|| webDoc.getSearchMapForKeywords().containsKey(wd)) {
				matchedDocs.add(webDoc);
			}
		}
		return matchedDocs;
	}

	public void setIndex(boolean byContents) {
		// set index by contents/keywords
		this.indexByContents = byContents;
	}

	@Override
	public String toString() {
		int totalWords = 0;
		if (indexByContents) {
			for (WebDoc webDoc : webDocs) {
				totalWords += webDoc.getSearchMapForContents().size();
			}
			return "WebIndex over contents contains " + totalWords
					+ " words from " + this.webDocs.size() + " documents";
		} else {
			for (WebDoc webDoc : webDocs) {
				totalWords += webDoc.getSearchMapForKeywords().size();
			}
			return "WebIndex over keywords contains " + totalWords
					+ " words from " + this.webDocs.size() + " documents";
		}
	}
}
