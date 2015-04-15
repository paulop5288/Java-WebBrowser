import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.Spring;

public class QueryBuilder {
	
	public static Query parse(String query) {
		if (!checkRegularForm(query)) {
			System.err.println("Query is not corrected.");
			return null;
		}
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
	
	public static Query parseInfixForm(String query) {
		return new AtomicQuery(query);
	}
	
	// check regular format
	public static boolean checkRegularForm(String query) {
		if (countOccurrences(query, '(') != countOccurrences(query, ')')) {
			return false;
		}
		return true;
	}
	
	// check infix format
	public static boolean checkInfixForm(String query) {
		if (query.contains("(") || query.contains(")")) {
			return false;
		}
		return true;
	}
	
	public static int countOccurrences(String haystack, char character) {
		if (haystack == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == character) {
				++count;
			}
		}
		return count;
	}


	public static void main(String[] args) {
		long startTime = System.nanoTime();
		String a = "and(western,and(country,fghjk,dfghj),or(fghj,ffsdghj,and(fghjk)))";
		System.out.println(countOccurrences(a, ')'));
		Query myQuery = QueryBuilder.parse(a);
		System.out.println(myQuery);
		long endTime = System.nanoTime();
		
		long durantion = (endTime - startTime);
		System.out.println("execution time is " + durantion / 1000000.0 + "ms");

	}
}
