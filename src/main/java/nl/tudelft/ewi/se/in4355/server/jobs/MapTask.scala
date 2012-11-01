package nl.tudelft.ewi.se.in4355.server.jobs

import com.google.gson.reflect.TypeToken

abstract class MapTask[F, T](code: String, input: List[F], resultType: TypeToken[T]) extends Task(code, input, resultType) {

  def handleAnswer(answer: T)

}
