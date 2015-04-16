import java.util.HashSet;
import java.util.Set;


public class OrQuery implements Query {
	private Set<Query> querys;
	public OrQuery(Set<Query> querys) {
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
