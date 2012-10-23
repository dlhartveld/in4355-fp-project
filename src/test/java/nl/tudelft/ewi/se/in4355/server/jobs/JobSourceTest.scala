package nl.tudelft.ewi.se.in4355.server.jobs

import org.junit.Test
import org.scalatest.junit.JUnitSuite

class JobSourceTest extends JUnitSuite {

  @Test def testThatNextDoesNotReturnTheSameListTwice {

    val js = new JobSource

    val firstFive = js.nextPackage.sentences
    val secondFive = js.nextPackage.sentences

    assert(firstFive.size == 5)
    assert(secondFive.size == 5)

    assert(firstFive != secondFive)

  }

}
