package nl.tudelft.ewi.se.in4355.server.jobs;

class Count{
	public final String word;
	public final int count;
	
	public Count() {
		this("", -1);
	}
	
	public Count(String word, int count) {
		this.word = word;
		this.count = count;
	}
}