package nl.tudelft.ewi.se.in4355.server.jobs.wordcount

import scala.collection.JavaConversions
import scala.collection.mutable.PriorityQueue

class WordIndex {

  val limit = 100;

  private var words = new PriorityQueue[WordCount]();

  def insert(word: WordCount) {
    this.synchronized {
      words.+=(word);
    }
  }

  def take: List[WordCount] = {
    this.synchronized {
      var amount = limit;
      if (words.size < limit) {
        amount = words.size;
      }
      var result = words.take(amount);
      words = words.drop(amount);
      return result.toList;
    }
  }

  def takeAll: scala.collection.immutable.List[WordCount] = {
    this.synchronized {
      var amount = words.size;
      var result = words.take(amount);
      words = words.drop(amount);
      return result.toList;
    }
  }

  def printContents() {
    val sortedWords = words.toList.sortBy(_.count * -1);
    println("Found " + sortedWords.size + " distinct words!");
    for (i <- 0 to sortedWords.size - 1) {
      val count = sortedWords(i);
      println(count.count + " x\t" + count.word);
    }
  }

  def size = words.size;

}