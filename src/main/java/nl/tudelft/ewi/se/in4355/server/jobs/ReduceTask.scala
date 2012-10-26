package nl.tudelft.ewi.se.in4355.server.jobs

import com.google.gson.reflect.TypeToken

abstract class ReduceTask[F, T](code: String, input: List[F], resultType: TypeToken[T]) extends Task(code, input, resultType) {
  
  var results = List[T]();
  
  def handleAnswer(answer: String) {
    submitReduceJob;
  }
  
  def submitReduceJob {
    if (results.size >= 2) {
//      submitData(results.take(2).toList);
      results = results.drop(2);
    }
  }
  
  def getResults: T = results(0);
  
}
