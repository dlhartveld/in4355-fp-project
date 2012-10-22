package nl.tudelft.ewi.se.in4355.server.jobs;

import java.util.ArrayList;
import java.util.List;

public class WordCount {
	public final int id;
	public final List<Count> wordCounts;
	
	public WordCount() {
		this(-1, new ArrayList<Count>());
	}
	
	public WordCount(int id, List<Count> wordCounts) {
		this.id = id;
		this.wordCounts = wordCounts;
	}
}