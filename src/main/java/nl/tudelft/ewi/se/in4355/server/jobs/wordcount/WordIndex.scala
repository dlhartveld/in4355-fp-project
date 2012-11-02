package nl.tudelft.ewi.se.in4355.server.jobs.wordcount

import scala.collection.JavaConversions
import java.util.PriorityQueue

class WordIndex {

  val limit = 100;

  private var words = new PriorityQueue[WordCount]();

  def insert(word: WordCount) {
    this.synchronized {
      words.add(word);
    }
  }

  def take: List[WordCount] = {
    this.synchronized {
      var amount = limit;
      if (words.size < limit) {
        amount = words.size;
      }

      return take(amount);
    }
  }

  private def take(amount: Int): List[WordCount] = {
    var results = List[WordCount]();
    for (i <- 0 to amount - 1) {
      results = results.+:(words.poll());
    }
    return results;
  }

  def takeAll: scala.collection.immutable.List[WordCount] = {
    this.synchronized {
      return take(words.size);
    }
  }

  def printContents() {
    val sortedWords = JavaConversions.collectionAsScalaIterable(words).toList.sortBy(_.count * -1);
    println("Found " + sortedWords.size + " distinct words!");
    for (i <- 0 to sortedWords.size - 1) {
      val count = sortedWords(i);
      println(count.count + " x\t\"" + count.word + "\"");
    }
  }

  def size = words.size;

}