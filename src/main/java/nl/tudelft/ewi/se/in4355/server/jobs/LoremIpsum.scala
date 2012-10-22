package nl.tudelft.ewi.se.in4355.server.jobs

class LoremIpsum {

  private var lines = allLines

  def nextFiveLines = {
    val result = lines.take(5)
    lines = lines.drop(5)
    result
  }

  def allLines = {
    scala.io.Source.fromInputStream(resourceStream).getLines().toList.filter((s: String) => !s.isEmpty())
  }

  private def resourceStream = {
    getClass.getResourceAsStream("/loremipsum.txt")
  }

}
