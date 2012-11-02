package nl.tudelft.ewi.se.in4355.server.jobs.wordcount

import scala.collection.JavaConversions
import com.google.gson.reflect.TypeToken
import nl.tudelft.ewi.se.in4355.server.jobs.MapTask
import nl.tudelft.ewi.se.in4355.server.jobs.TaskTracker
import nl.tudelft.ewi.se.in4355.server.jobs.ReduceTask
import java.util.concurrent.Callable

class WordCountJob(val inputFile: String) extends Callable[WordIndex] {

  val tracker = TaskTracker;

  def call(): WordIndex = reduceAll(map());

  private def map(): WordIndex = {
    var results = new WordIndex();

    val data = readLines(inputFile).grouped(500).map((x) => JavaConversions.seqAsJavaList(x)).toList;
    println("Mapping " + data.size + " data packages");

    val mapTask = new MapTask[java.util.List[String], WordCountList](read("wordcount-mappercombiner.js"), data, new TypeToken[WordCountList]() {}) {
      def handleAnswer(result: WordCountList) {
        for (index <- 0 to result.wordCounts.size - 1) {
          val count = result.wordCounts.get(index);
          results.insert(count);
        }
      }
    };
    tracker.submitTask(mapTask);

    while (!mapTask.completed) {
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
      var groupedData = reduceData.grouped(10000).map((x) => JavaConversions.seqAsJavaList(x)).toList;
      println("Reducing " + groupedData.size + " data packages (" + size + " words total)");

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
    while (!reduceTask.completed) {
      Thread.sleep(100);
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
