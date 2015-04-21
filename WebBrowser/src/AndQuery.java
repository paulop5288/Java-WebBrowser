// This file should be assessed for Stage 2

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AndQuery implements Query {
	private List<Query> querys;
	public AndQuery(List<Query> querys) {
		this.querys = querys;
	}

	@Override
	public Set<WebDoc> matches(WebIndex wind) {
		// empty set
		if (querys.size() < 2) {
			return new HashSet<>();
		}
		
		boolean startMatch = false;
		Set<WebDoc> matchedWebDocs = new HashSet<>();

		for (Query query : querys) {
			if (!startMatch) {
				matchedWebDocs = query.matches(wind);
				startMatch = true;
				continue;
			}
			// match start from the second subquery
			Set<WebDoc> currentWebDocs = query.matches(wind);
			matchedWebDocs = andMatch(matchedWebDocs, currentWebDocs);
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
