package nl.tudelft.ewi.se.in4355.server.jobs.wordcount;

class WordCount{
	public final String word;
	public final int count;
	
	public WordCount() {
		this("", -1);
	}
	
	public WordCount(String word, int count) {
		this.word = word;
		this.count = count;
	}
}