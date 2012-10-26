package nl.tudelft.ewi.se.in4355.server.jobs.wordcount;

import java.util.ArrayList;
import java.util.List;

public class WordCountList {
	
	public final int id;
	public final List<WordCount> wordCounts;
	
	public WordCountList() {
		this(-1, new ArrayList<WordCount>());
	}
	
	public WordCountList(int id, List<WordCount> wordCounts) {
		this.id = id;
		this.wordCounts = wordCounts;
	}
	
}