package nl.tudelft.ewi.se.in4355.server.jobs

import com.google.gson.reflect.TypeToken

class WordCounterJob(val inputFile: String) {

  val tracker = TaskTracker;
  
  def submit() {
    
    var results = List[WordCount]();
    
    val mapTask = new MapTask[String, WordCount](read("wordcounter-1.js"), readLines(inputFile), new TypeToken[WordCount]() {}) {
      def handleAnswer(result: WordCount) {
        results.+:(result);
      }
    };
    tracker.submitTask(mapTask);
    
    while (mapTask.hasNext) { }
    
    println(results);
  }
  
  private def read(fileName: String) = {
    readLines(fileName).foldLeft("")((x, y) => (x.+(y)));
  }
  
  private def readLines(fileName: String) = {
    scala.io.Source.fromInputStream(resourceStream(fileName)).getLines().toList.filter((s: String) => !s.isEmpty())
  }

  private def resourceStream(fileName: String) = {
    getClass.getResourceAsStream("/" + fileName)
  }
  
}
