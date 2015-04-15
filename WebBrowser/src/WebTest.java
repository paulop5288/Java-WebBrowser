import java.util.Set;


public class WebTest {

	public static void
	main(String argv[]) {
		//bit of a cheat in here in that we create our own webdocs without parsing them.
		//important part is that for assignment 2 you need a working web index, and this was
		//the quickest way for me to supply one.
		
		WebDoc document1 = WebDoc.getTestWebDoc("http://www.shef.ac.uk/trains.html",
										        new String[]{"this", "is", "the", "age", "on", "the", "train"},
										        new String[]{"travel", "trains"});
		WebDoc document2 = WebDoc.getTestWebDoc("http://www.shef.ac.uk/buses.html",
												new String[]{"the", "wheels", "on", "the", "bus", "go", "round"},
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
		
		
		WebIndex content = new WebIndex(WebIndexType.CONTENT);
		WebIndex keywords = new WebIndex(WebIndexType.KEYWORDS);
		
		for (WebDoc doc: docs) {
			System.out.println(doc.toString());
			content.add(doc);
			keywords.add(doc);
		}
		System.out.println(content.toString());
		System.out.println(keywords.toString());
		
		runMatches(keywords, "travel");
		runMatches(content, "are");
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
