package nl.tudelft.ewi.se.in4355.server.jobs.wordcount

import scala.collection.JavaConversions
import com.google.gson.reflect.TypeToken
import nl.tudelft.ewi.se.in4355.server.jobs.MapTask
import nl.tudelft.ewi.se.in4355.server.jobs.TaskTracker
import nl.tudelft.ewi.se.in4355.server.jobs.ReduceTask

class WordCountJob(val inputFile: String) {

  val tracker = TaskTracker;

  def execute() {
    printResults(reduceAll(map()));
  }

  private def map(): WordIndex = {
    var results = new WordIndex();

    val data = readLines(inputFile).grouped(50).map((x) => JavaConversions.seqAsJavaList(x)).toList;
    val mapTask = new MapTask[java.util.List[String], WordCountList](read("wordcount-mappercombiner.js"), data, new TypeToken[WordCountList]() {}) {
      def handleAnswer(result: WordCountList) {
        for (index <- 0 to result.wordCounts.size - 1) {
          val count = result.wordCounts.get(index);
          results.insert(count);
        }
      }
    };
    tracker.submitTask(mapTask);

    while (mapTask.hasNext) {
      Thread.sleep(100);
    }

    return results;
  }

  private def reduceAll(results: WordIndex): WordIndex = {
    var size = 0;
    var prevSize = -1;

    while (size != prevSize) {
      var reduceData = results.takeAll;
      prevSize = size;
      size = reduceData.size;
      var groupedData = reduceData.grouped(100).map((x) => JavaConversions.seqAsJavaList(x)).toList;
      reduce(results, groupedData);
    }

    return results;
  }

  private def reduce(results: WordIndex, groupedData: List[java.util.List[WordCount]]) {
    val reduceTask = new ReduceTask[java.util.List[WordCount], WordCountList](read("wordcount-reducer.js"), groupedData, new TypeToken[WordCountList]() {}) {
      def handleAnswer(result: WordCountList) {
        for (index <- 0 to result.wordCounts.size - 1) {
          val count = result.wordCounts.get(index);
          results.insert(count);
        }
      }
    };

    tracker.submitTask(reduceTask);
    while (reduceTask.hasNext) {
      Thread.sleep(100);
    }
  }

  private def printResults(results: WordIndex) {
    val words = results.takeAll;
    println("Found " + words.size + " distinct words!");
    for (i <- 0 to words.size - 1) {
      val count = words(i);
      println(count.count + " x\t" + count.word);
    }
  }

  private def read(fileName: String) = {
    readLines(fileName).foldLeft("")((x, y) => (x.+("\n" + y)));
  }

  private def readLines(fileName: String) = {
    scala.io.Source.fromInputStream(resourceStream(fileName)).getLines().toList.filter((s: String) => !s.isEmpty())
  }

  private def resourceStream(fileName: String) = {
    getClass.getResourceAsStream("/" + fileName)
  }

}
