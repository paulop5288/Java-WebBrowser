// This file should NOT be assessed
import java.util.HashSet;
import java.util.Set;


public class NotQuery implements Query {
	private Query query;

	public NotQuery(Query query) {
		this.query = query;
	}

	@Override
	public Set<WebDoc> matches(WebIndex wind) {
		
		// allWebDocs is not modifiable
		Set<WebDoc> allWebDocs = wind.getAllDocuments();
		
		// matched from subquery
		Set<WebDoc> noWebDocs = query.matches(wind);
		
		Set<WebDoc> matchedWebDocs = new HashSet<>();
		
		for (WebDoc webDoc : allWebDocs) {
			if (!noWebDocs.contains(webDoc)) {
				matchedWebDocs.add(webDoc);
			}
		}
		return matchedWebDocs;
	}
	
	@Override
	public String toString() {
		if (query instanceof AtomicQuery) {
			return "NotQuery : " + ((AtomicQuery)query).getQueryString() ;
		}
		return "NotQuery : [" + query + "]";
	}
}
