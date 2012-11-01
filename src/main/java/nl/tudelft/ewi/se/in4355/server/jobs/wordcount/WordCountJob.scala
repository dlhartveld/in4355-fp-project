package nl.tudelft.ewi.se.in4355.server.jobs.wordcount

import scala.collection.JavaConversions

import com.google.gson.reflect.TypeToken

import nl.tudelft.ewi.se.in4355.server.jobs.MapTask
import nl.tudelft.ewi.se.in4355.server.jobs.TaskTracker

class WordCountJob(val inputFile: String) {

  val tracker = TaskTracker;

  def submit(waitForCompletion: Boolean) {

    var results = new WordIndex();

    val data = readLines(inputFile).grouped(5).map((x) => JavaConversions.seqAsJavaList(x)).toList;
    val mapTask = new MapTask[java.util.List[String], WordCountList](read("wordcounter-1.js"), data, new TypeToken[WordCountList]() {}) {
      def handleAnswer(result: WordCountList) {
        for (index <- 0 to result.wordCounts.size) {
          val count = result.wordCounts.get(index);
          results.insert(count);
        }
      }
    };
    tracker.submitTask(mapTask);

    while (mapTask.hasNext) {}

    var size = 0;
    var prevSize = -1;

    while (size != prevSize) {
      var reduceData = results.takeAll;
      prevSize = size;
      size = reduceData.size;
      var groupedData = reduceData.grouped(100).map((x) => JavaConversions.seqAsJavaList(x)).toList;
      val reduceTask = new MapTask[java.util.List[WordCount], WordCountList](read("wordcounter-2.js"), groupedData, new TypeToken[WordCountList]() {}) {
        def handleAnswer(result: WordCountList) {
          for (index <- 0 to result.wordCounts.size) {
            val count = result.wordCounts.get(index);
            results.insert(count);
          }
        }
      };
    }

    println("Found " + results.size + " distinct words!");
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
