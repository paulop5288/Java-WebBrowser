import java.util.ArrayList;

public class QueryBuilder {
	public static Query parse(String query) {
		
		if (!query.contains("(") && !query.contains(")")) {
			return new AtomicQuery(query);
		}
		
		if (query.startsWith("not")) {
			return new NotQuery(query.substring(3));
		}
		
		if (query.startsWith("and")) {
			ArrayList<Query> andQuerys = new ArrayList<>();
			query = query.replaceFirst("and", "").trim();
			query = query.substring(1, query.length() - 1);
			String[] queryStrings = QueryBuilder.split(query);
			for (String singleQuery : queryStrings) {
				andQuerys.add(QueryBuilder.parse(singleQuery));
			}
			return new AndQuery(andQuerys);
		}
		
		if (query.startsWith("or")) {
			ArrayList<Query> orQuerys = new ArrayList<>();
			query = query.replaceFirst("or", "").trim();
			query = query.substring(1, query.length() - 1);
			String[] queryStrings = QueryBuilder.split(query);
			for (String singleQuery : queryStrings) {
				orQuerys.add(QueryBuilder.parse(singleQuery));
			}
			return new OrQuery(orQuerys);
		}

		return null;
	}
	
	public static String[] split(String query) {
		ArrayList<String> queryStrings = new ArrayList<>();
		int bracketCount = 0;
		int lastIndex = 0;
		for (int i = 0; i < query.length(); i++) {
			char charCursor = query.charAt(i);
			if (charCursor == ',' && bracketCount == 0) {
				queryStrings.add(query.substring(lastIndex, i));
				lastIndex = i + 1;
			} else if (i == query.length() - 1) {
				queryStrings.add(query.substring(lastIndex, i + 1));
			}
			if (charCursor == '(') {
				bracketCount++;
			}
			if (charCursor == ')') {
				bracketCount--;
			}
		}
		return queryStrings.toArray(new String[queryStrings.size()]);
	}
	
	public static void main(String[] args) {
//		String a = "and(western,and(country,fghjk,dfghj),or(fghj,ffsdghj,and(fghjk)))";
//		Query myQuery = QueryBuilder.parse(a);
//		System.out.println(myQuery);
		String a = "hello";
		String b = "hello";
		if (a == b) {
			System.out.println("same");
		}
	}
}
