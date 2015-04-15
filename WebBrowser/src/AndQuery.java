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
		Set<WebDoc> allWebDocs = new HashSet<>();
		Set<WebDoc> matchedWebDocs = new HashSet<>();
		for (Query query : querys) {
			Set<WebDoc> tmpWebDocs = query.matches(wind);
			for (WebDoc webDoc : tmpWebDocs) {
				if (allWebDocs.contains(webDoc)) {
					matchedWebDocs.add(webDoc);
				}
			}
			allWebDocs.addAll(tmpWebDocs);
		}
		return matchedWebDocs;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "AndQuery : " + querys.toString();
	}

}
