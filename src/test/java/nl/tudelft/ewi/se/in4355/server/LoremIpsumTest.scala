package nl.tudelft.ewi.se.in4355.server

import org.scalatest.junit.JUnitSuite
import org.junit.Test

class LoremIpsumTest extends JUnitSuite {

  @Test def test {
    val lines = LoremIpsum.lines

    assert(lines.size == 150)
  }

}
