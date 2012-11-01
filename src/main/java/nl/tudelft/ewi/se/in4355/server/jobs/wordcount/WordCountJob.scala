package nl.tudelft.ewi.se.in4355.server.jobs.wordcount

import scala.collection.JavaConversions
import com.google.gson.reflect.TypeToken
import nl.tudelft.ewi.se.in4355.server.jobs.MapTask
import nl.tudelft.ewi.se.in4355.server.jobs.TaskTracker

class WordCountJob(val inputFile: String) {

  val tracker = TaskTracker;

  def submit() {

    var results = List[WordCount]();

    val data = readLines(inputFile).grouped(5).map((x) => JavaConversions.seqAsJavaList(x)).toList;
    val mapTask = new MapTask[java.util.List[String], WordCount](read("wordcount-mappercombiner.js"), data, new TypeToken[WordCount]() {}) {
      def handleAnswer(result: WordCount) {
        results.+:(result);
      }
    };
    tracker.submitTask(mapTask);

    while (mapTask.hasNext) {}
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
