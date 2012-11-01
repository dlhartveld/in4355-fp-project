package nl.tudelft.ewi.se.in4355.server.jobs.wordcount;

class WordCount implements Comparable<WordCount> {
	public final String word;
	public final int count;

	public WordCount() {
		this("", -1);
	}

	public WordCount(String word, int count) {
		this.word = word;
		this.count = count;
	}

	@Override
	public int compareTo(WordCount other) {
		return word.compareTo(other.word);
	}

}