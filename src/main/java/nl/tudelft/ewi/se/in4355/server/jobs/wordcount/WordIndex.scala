package nl.tudelft.ewi.se.in4355.server.jobs.wordcount

import scala.collection.JavaConversions

class WordIndex {

  val limit = 100;

  private var words = List[WordCount]();

  def insert(word: WordCount) {
    this.synchronized {
      words = words.+:(word).sortBy(_.word);
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
      return result;
    }
  }

  def takeAll: scala.collection.immutable.List[WordCount] = {
    this.synchronized {
      var amount = words.size;
      var result = words.take(amount);
      words = words.drop(amount);
      return result;
    }
  }

  def size = words.size;

}