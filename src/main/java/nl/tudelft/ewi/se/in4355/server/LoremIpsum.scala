package nl.tudelft.ewi.se.in4355.server

object LoremIpsum {

  def lines = {
    scala.io.Source.fromInputStream(resourceStream).getLines().toList.filter((s: String) => !s.isEmpty())
  }

  def resourceStream = {
    getClass.getResourceAsStream("/loremipsum.txt")
  }

}
