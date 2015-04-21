// This file should be assessed for Stage 2

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QueryBuilder {
	
	public static Query parse(String query) throws BadQueryFormatException {
		// modify query 
		query = query.trim().toLowerCase();
		if (!checkRegularForm(query)) {
			throw new BadQueryFormatException();
		}
		
		// start parsing
		// atomic query
		if (!query.contains("(") && !query.contains(")")) {
			// form is already checked
				return new AtomicQuery(query);
		}
		
		// not query
		if (query.startsWith("not")) {			
			query = query.substring(query.indexOf("(") + 1, query.lastIndexOf(")")).trim();
			return new NotQuery(QueryBuilder.parse(query));
		}
		
		// and query
		if (query.startsWith("and")) {
			ArrayList<Query> andQuerys = new ArrayList<>();
			query = query.substring(query.indexOf("(") + 1, query.lastIndexOf(")")).trim();
			String[] queryStrings = QueryBuilder.split(query);
			for (String singleQuery : queryStrings) {
				andQuerys.add(QueryBuilder.parse(singleQuery));
			}
			return new AndQuery(andQuerys);
		}
		
		// or query
		if (query.startsWith("or")) {
			ArrayList<Query> orQuerys = new ArrayList<>();
			query = query.substring(query.indexOf("(") + 1, query.lastIndexOf(")")).trim();
			String[] queryStrings = QueryBuilder.split(query);
			for (String singleQuery : queryStrings) {
				orQuerys.add(QueryBuilder.parse(singleQuery));
			}
			return new OrQuery(orQuerys);
		}
		throw new BadQueryFormatException();
	}
	
	// split prefix form
	public static String[] split(String query) {
		// split by ","
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
			// "," inside an "( )" will be ignored
			if (charCursor == '(') {
				bracketCount++;
			}
			if (charCursor == ')') {
				bracketCount--;
			}
		}
		return queryStrings.toArray(new String[queryStrings.size()]);
	}

	// parse infix form
	public static Query parseInfixForm(String query) throws BadQueryFormatException {
		if (!checkInfixForm(query)) {
			throw new BadQueryFormatException();
		}
		
		// start parsing
		// and query
		if (query.contains(" and ")) {
			String[] queryComponents = query.split(" and ");
			ArrayList<Query> querys = new ArrayList<>();
			for (String queryString : queryComponents) {
				querys.add(parseInfixForm(queryString));
			}
			return new AndQuery(querys);
		}
		
		// or query
		if (query.contains(" or ")) {
			String[] queryComponents = query.split(" or ");
			ArrayList<Query> querys = new ArrayList<>();
			for (String queryString : queryComponents) {
				querys.add(parseInfixForm(queryString));
			}
			return new OrQuery(querys);
		}
		
		// not query
		if (query.contains("not ")) {
			query = query.substring(4);
			return new NotQuery(QueryBuilder.parseInfixForm(query));
		}
		if (!checkRegularCharacter(query)) {
			throw new BadQueryFormatException();
		}
		
		// atomic query
		return new AtomicQuery(query);
	}
	
	// check regular format
	public static boolean checkRegularForm(String query) throws BadQueryFormatException{
		// check number of brackets
		if (countOccurrences(query, '(') != countOccurrences(query, ')')) {
			return false;
		}
		if (!query.contains("(") || !query.contains(")")) {
			return checkRegularCharacter(query);
		}
		return true;
	}
	
	// check if a query contains regular word only
	public static boolean checkRegularCharacter(String query) {
		// use regex to check
		Pattern pattern = Pattern.compile("[\\W]");
		Matcher matcher = pattern.matcher(query);
		if (matcher.find()) {
			return false;
		}
		return true;
	}
	
	// check infix format
	public static boolean checkInfixForm(String query) {
		if (query.contains("(") || query.contains(")")) {
			return false;
		}
		if (query.contains(" or ") && query.contains(" and ")) {
			return false;
		}

		// check if the query contains word and space only
		Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");
		Matcher matcher = pattern.matcher(query);
		if (matcher.find()) {	
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
		String a = "sex and drugs and rock and roll";

//		try {
//			Query myQuery = QueryBuilder.parse(a);
//			System.out.println(myQuery);
//		} catch (Exception e) {
//			System.out.println(e);
//		}

		try {
			QueryBuilder.parseInfixForm(a);
		} catch (Exception e) {
			System.out.println(e);
		}

		long endTime = System.nanoTime();
		
		long durantion = (endTime - startTime);
		System.out.println("execution time is " + durantion / 1000000.0 + "ms");

	}
}
