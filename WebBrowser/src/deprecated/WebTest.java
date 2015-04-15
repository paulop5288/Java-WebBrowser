package deprecated;
import java.util.ArrayList;

//public abstract class WebTest {


//	public static void main(String[] args) {
//
//		CommandReader commandReader = new CommandReader();
//		
//		ArrayList<String> files = commandReader.readFileArgs(args[0]);
//		
//		WebIndex webIndex = null;
//		
//		// set index by contents
//		webIndex = new WebIndex(true);
//		
//		for (String file : files) {
//			try {
//				WebDoc webDoc = new WebDoc(file);
//				webIndex.add(webDoc);
//				System.out.println(webDoc);
//			} catch (Exception e) {
//				System.err.println(file + " is wrong.");
//			}
//		}
//
//		System.out.println("-------------");
//		System.out.println(webIndex);
//		
//		// set index by keywords
//		webIndex.setIndex(false);
//		System.out.println(webIndex);
//	}
//}
