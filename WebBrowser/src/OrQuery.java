// This file should NOT be assessed
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OrQuery implements Query {
	private List<Query> querys;
	public OrQuery(List<Query> querys) {
		this.querys = querys;
	}

	@Override
	public Set<WebDoc> matches(WebIndex wind) {
		Set<WebDoc> matchedWebDocs = new HashSet<>();
		for (Query query : querys) {
			matchedWebDocs.addAll(query.matches(wind));
		}
		return matchedWebDocs;
	}
	
	@Override
	public String toString() {
		return "OrQuery : " + querys.toString();
	}
	
}
