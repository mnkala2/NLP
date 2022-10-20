package com.ikhokha.techcheck;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

public class Main {
	
	private static Map<String, Integer> totalResults = new Hashtable<>();

    public static void main(String[] args) {
		//Variables needed for  running the solution on a single thread
        /*Map<String, Integer> totalResults = new HashMap<>();
		Map<String, Integer> fileResults = new HashMap<>();
		*/
        File docPath = new File("docs");
        File[] commentFiles = docPath.listFiles((d, n) -> n.endsWith(".txt"));

        for (File commentFile : commentFiles) {
			//This run the solution on single  thread
			/*CommentAnalyzer commentAnalyzer = new CommentAnalyzer(commentFile);
			fileResults = commentAnalyzer.analyze(fileResults);
			addReportResults(fileResults, totalResults);*/

			addReportResults(totalResults, totalResults);
            new CommentAnalyzer(commentFile).run();

        }

        System.out.println("RESULTS\n=======");
        totalResults.forEach((k, v) -> System.out.println(k + " : " + v));
    }
	
	/**
	 * This method adds the result counts from a source map to the target map 
	 * @param source the source map
	 * @param target the target map
	 */
	private static void addReportResults(Map<String, Integer> source, Map<String, Integer> target) {

		for (Map.Entry<String, Integer> entry : source.entrySet()) {
			target.put(entry.getKey(), entry.getValue());
		}
		
	}
	
    public static void setTotalResults(Map<String, Integer> totalResults) {
        Main.totalResults = totalResults;
    }

    public static Map<String, Integer> getTotalResults() {
        return totalResults;
    }

}
