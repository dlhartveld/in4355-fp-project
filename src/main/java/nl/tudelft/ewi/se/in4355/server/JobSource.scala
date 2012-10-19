package nl.tudelft.ewi.se.in4355.server

class JobSource {

  var input = LoremIpsum

  var current = 0

  def next = {
    input.nextFiveLines
  }

}
