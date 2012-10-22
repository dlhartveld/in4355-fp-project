package nl.tudelft.ewi.se.in4355.server.jobs

class JobSource {

  val input = new LoremIpsum()

  def next = {
    nextFiveParagraphs
  }

  def nextFiveParagraphs = {
    "{" + input.nextFiveLines.map((x) => "\"paragraph\": \"" + x + "\"")
      .foldLeft("")((l, r) => l + "," + r).tail + "}"
  }

}
