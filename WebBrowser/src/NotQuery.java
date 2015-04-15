import java.util.Set;


public class NotQuery implements Query {
	private String queryString;

	public NotQuery(String query) {
		queryString = query;
	}

	@Override
	public Set<WebDoc> matches(WebIndex wind) {
		Set<WebDoc> matchedWebDocs = wind.getAllDocuments();
		Set<WebDoc> noWebDocs = wind.matches(queryString);
		matchedWebDocs.removeAll(noWebDocs);
		return matchedWebDocs;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "NotQuery : " + queryString;
	}
}
