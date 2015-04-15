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
	
	public static void main(String[] args) {
		String[] all = {"111 ", "222 ", "333 ", "444 ", "555 "};
		Set<String> sentence = new HashSet<>();
		Set<String> s2 = new HashSet<>();
		sentence.add(all[0]);
		sentence.add(all[1]);
		sentence.add(all[2]);
		s2.add(all[2]);
		s2.add(all[3]);
		s2.add(all[4]);
		sentence.addAll(s2);
		for (String string : sentence) {
			System.out.println(string);
		}
	}
}
