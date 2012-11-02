package nl.tudelft.ewi.se.in4355.server.jobs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class Task[F, T](val code: String, val input: List[F], val resultType: TypeToken[T]) {

  private var packages = input;
  private var indices = listIndices;
  private var index = 0;

  def nextBatch: Input[F] = {
    this.synchronized {
      if (index >= 0) {
        val value = new Input[F](index, packages(index));
        updateIndex;
        return value;
      }
      return new Input[F]();
    }
  }

  def getCode: String = code;

  private def updateIndex {
    if (!hasNext) {
      index = -1;
      return ;
    }

    while (true) {
      index += 1;
      if (index == packages.size) {
        index = 0;
      }

      if (indices.contains(index)) {
        return ;
      }
    }
  }

  def hasNext: Boolean = {
    this.synchronized {
      return !indices.isEmpty;
    }
  }

  def submitData(data: List[F]) {
    this.synchronized {
      val start = packages.size;
      packages = packages.++(data)
      indices = indices.++(new Range(start, packages.size - 1, 1).toSet);
    }
  }

  def markDone(id: Int, result: String) {
    this.synchronized {
      indices = indices.-(id);
      handleAnswer(new Gson().fromJson(result, resultType.getType()))
    }
  }

  def handleAnswer(answer: T);

  private def listIndices: Set[Int] = {
    new Range(0, packages.length, 1).toSet;
  }

  def completed: Boolean = indices.isEmpty;

}
