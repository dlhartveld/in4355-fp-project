package nl.tudelft.ewi.se.in4355.server

object LoremIpsum {

  private var lines = allLines

  private var counter = 0;

  def nextFiveLines = {
    val result = lines.take(5)
    lines = lines.drop(5)
    result
  }

  def allLines = {
    scala.io.Source.fromInputStream(resourceStream).getLines().toList.filter((s: String) => !s.isEmpty())
  }

  def resourceStream = {
    getClass.getResourceAsStream("/loremipsum.txt")
  }

}
