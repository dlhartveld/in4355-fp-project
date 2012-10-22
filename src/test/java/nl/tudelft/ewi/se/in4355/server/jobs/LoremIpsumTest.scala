package nl.tudelft.ewi.se.in4355.server.jobs

import org.junit.Test
import org.scalatest.junit.JUnitSuite

class LoremIpsumTest extends JUnitSuite {

  @Test def testThatLoremIpsumContains150lines {
    val lines = new LoremIpsum().allLines

    assert(lines.size == 150)
  }

  @Test def testThatLoremIpsumCanBeReadEntirely {

    val li = new LoremIpsum()

    var next = li.nextFiveLines
    while (next != Nil) {
      next = li.nextFiveLines
      next.map(println)
    }

  }

}
