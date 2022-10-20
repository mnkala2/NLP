package com.ikhokha.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

public class CommentAnalyzer {
	
	private File file;
	
	public CommentAnalyzer(File file) {
		this.file = file;
	}
	
	 public synchronized Map<String, Integer> analyze(Map<String, Integer> currentMap) {

	        Map<String, Integer> resultsMap = currentMap;

	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

	            String line = null;
	            while ((line = reader.readLine()) != null) {

	                if (line.length() < 15) {

	                    incOccurrence(resultsMap, "SHORTER_THAN_15");

	                } else if (line.contains("Mover")) {

	                    incOccurrence(resultsMap, "MOVER_MENTIONS");

	                } else if (line.contains("Shaker")) {

	                    incOccurrence(resultsMap, "SHAKER_MENTIONS");

	                } else if (line.contains("?")) {

	                    incOccurrence(resultsMap, "QUESTIONS");

	                } else if (containsUrl(line)) {

	                    incOccurrence(resultsMap, "SPAM");

	                }
	            }

	        } catch (FileNotFoundException e) {
	            System.out.println("File not found: " + file.getAbsolutePath());
	            e.printStackTrace();
	        } catch (IOException e) {
	            System.out.println("IO Error processing file: " + file.getAbsolutePath());
	            e.printStackTrace();
	        }

	        return resultsMap;

	    }

	    /**
	     * This method checks if comment contains  URL
	     **/
	    private boolean containsUrl(String comment) {
	        String regexString = "\\b(https://|www[.])[A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
	        Pattern pattern = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
	        return pattern.matcher(comment).find();
	    }

	    /**
	     * This method increments a counter by 1 for a match type on the countMap. Uninitialized keys will be set to 1
	     *
	     * @param countMap the map that keeps track of counts
	     * @param key      the key for the value to increment
	     */
	    private void incOccurrence(Map<String, Integer> countMap, String key) {

	        countMap.putIfAbsent(key, 0);
	        countMap.put(key, countMap.get(key) + 1);
	    }
	    
	    public void run() {
	        Map<String, Integer> map = Main.getTotalResults();
	        map = analyze(map);
	        Main.setTotalResults(map);
	    }

}
