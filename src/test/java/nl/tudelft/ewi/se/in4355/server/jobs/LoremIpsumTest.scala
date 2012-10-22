package nl.tudelft.ewi.se.in4355.server.jobs

import org.junit.Test
import org.scalatest.junit.JUnitSuite

class LoremIpsumTest extends JUnitSuite {

  @Test def testThatLoremIpsumContains150lines {
    val lines = LoremIpsum.allLines

    assert(lines.size == 150)
  }

}
