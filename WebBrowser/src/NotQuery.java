import java.util.HashSet;
import java.util.Set;


public class NotQuery implements Query {
	private Query query;

	public NotQuery(Query query) {
		this.query = query;
	}

	@Override
	public Set<WebDoc> matches(WebIndex wind) {
		Set<WebDoc> allWebDocs = wind.getAllDocuments();
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
		if (query instanceof NotQuery) {
			return "NotQuery : [" + query + "]";
		}
		return "NotQuery : " + ((AtomicQuery)query).getQueryString() ;
	}
}
