import java.util.HashSet;
import java.util.Set;


public class AndQuery implements Query {
	private Set<Query> querys;
	public AndQuery(Set<Query> querys) {
		this.querys = querys;
	}

	@Override
	public Set<WebDoc> matches(WebIndex wind) {
		if (querys.size() < 2) {
			return new HashSet<>();
		}
		
		boolean startMatch = false;
		Set<WebDoc> lastWebDocs = null;
		Set<WebDoc> matchedWebDocs = new HashSet<>();

		for (Query query : querys) {
			if (!startMatch) {
				lastWebDocs = query.matches(wind);
				startMatch = true;
				continue;
			}
			Set<WebDoc> currentWebDocs = query.matches(wind);
			matchedWebDocs = andMatch(lastWebDocs, currentWebDocs);
			lastWebDocs = currentWebDocs;
		}
		return matchedWebDocs;
	}
	
	private Set<WebDoc> andMatch(Set<WebDoc> firstDocs, Set<WebDoc> secondDocs) {
		Set<WebDoc> tempSet = new HashSet<>();
		for (WebDoc webDoc : firstDocs) {
			if (secondDocs.contains(webDoc)) {
				tempSet.add(webDoc);
			}
		}
		return tempSet;
	}
	
	@Override
	public String toString() {
		return "AndQuery : " + querys.toString();
	}

}
