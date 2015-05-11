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
	
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.subList(2, list.size()).clear();
		System.out.println(list);
		
	}
	
}
