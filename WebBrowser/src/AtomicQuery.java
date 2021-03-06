// This file should NOT be assessed

import java.util.ArrayList;
import java.util.Set;


public class AtomicQuery implements Query {
	private String queryString;
	public AtomicQuery(String query) {
		queryString = query;
	}

	@Override
	public Set<WebDoc> matches(WebIndex wind) {
		return wind.matches(queryString);
	}
	
	@Override
	public String toString() {
		return "AtomicQuery : " + queryString;
	}

	public String getQueryString() {
		return queryString;
	}
	
}
