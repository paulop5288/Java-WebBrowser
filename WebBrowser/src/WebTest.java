// This file comes from Stage 1

import java.util.ArrayList;
import java.util.Set;


public class WebTest {

	public static void
	main(String args[]) {
		//bit of a cheat in here in that we create our own webdocs without parsing them.
		//important part is that for assignment 2 you need a working web index, and this was
		//the quickest way for me to supply one.
		
		WebDoc document1 = WebDoc.getTestWebDoc("http://www.shef.ac.uk/trains.html",
										        new String[]{"this", "is", "the", "age", "on", "the", "train"},
										        new String[]{"travel", "trains"});
		WebDoc document2 = WebDoc.getTestWebDoc("http://www.shef.ac.uk/buses.html",
												new String[]{"tbhe", "wheels", "on", "the", "bus", "go", "round"},
												new String[]{"travel", "bus"});
		WebDoc document3 = WebDoc.getTestWebDoc("http://www.shef.ac.uk/planes.html",
		        								new String[]{"planes", "are", "new", "and", "fly", "high", "up"},
		        								new String[]{"aeroplane", "plane"});
		WebDoc document4 = WebDoc.getTestWebDoc("http://www.shef.ac.uk/cars.html",
		        								new String[]{"cars", "are", "fun", "to", "drive", "on", "tracks"},
		        								new String[]{"cars", "travel"});
		WebDoc document5 = WebDoc.getTestWebDoc("http://www.shef.ac.uk/bikes.html",
		        								new String[]{"bmx", "racer", "chopper", "moutain", "are", "all", "bikes"},
		        								new String[]{"bikes", "travel"});
		WebDoc[] docs = new WebDoc[] {document1, document2, document3, document4, document5};
		
		CommandReader commandReader = new CommandReader();
		ArrayList<String> queryStrings = commandReader.readFileArgs(args[0]);
		ArrayList<Query> querys = new ArrayList<>();
		
		for (String queryString : queryStrings) {
			try {
				Query query = QueryBuilder.parse(queryString);
				querys.add(query);
				System.out.println(query);
			} catch (Exception e) {
				System.err.println("Query is not corrected.");
			}
		}
		
		
		System.out.println("----------------------------------\n");
		WebIndex content = new WebIndex(WebIndexType.CONTENT);
		for (WebDoc doc: docs) {
			content.add(doc);
		}
		Query firstQuery = querys.get(querys.size() - 1);
		Query secondQuery = querys.get(querys.size() - 2);
		System.out.println(secondQuery);
		Set<WebDoc> matchedDocs = secondQuery.matches(content);
		for (WebDoc webDoc : matchedDocs) {
			System.out.println(webDoc);
		}
		
		System.out.println("\n"+firstQuery);
		matchedDocs = firstQuery.matches(content);
		for (WebDoc webDoc : matchedDocs) {
			System.out.println(webDoc);
		}

	}

	public static void
	runMatches(WebIndex the_index, String the_word) {
		Set<WebDoc> results = the_index.matches(the_word);
		System.out.println("query returned "+ results.size()+ " matches");
		for (WebDoc doc: results) {
			System.out.println(doc.getUrl());
		}
	}
	
}
