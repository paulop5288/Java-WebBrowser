import java.util.ArrayList;
import java.util.HashSet;

public class QueryBuilder {
	public static Query parse(String query) {
		
		if (!query.contains("(") && !query.contains(")")) {
			return new AtomicQuery(query);
		}
		
		if (query.startsWith("not")) {
			return new NotQuery(query.substring(3));
		}
		
		if (query.startsWith("and")) {
			HashSet<Query> andQuerys = new HashSet<>();
			query = query.replaceFirst("and", "").trim();
			query = query.substring(1, query.length() - 1);
			String[] queryStrings = QueryBuilder.split(query);
			for (String singleQuery : queryStrings) {
				andQuerys.add(QueryBuilder.parse(singleQuery));
			}
			return new AndQuery(andQuerys);
		}
		
		if (query.startsWith("or")) {
			HashSet<Query> orQuerys = new HashSet<>();
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
		long startTime = System.nanoTime();
		String a = "and(western,and(country,fghjk,dfghj),or(fghj,ffsdghj,and(fghjk)))";
		Query myQuery = QueryBuilder.parse(a);
		System.out.println(myQuery);
		long endTime = System.nanoTime();
		
		long durantion = (endTime - startTime);
		System.out.println("execution time is " + durantion / 1000000.0 + "ms");
		

	}
}
