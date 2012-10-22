package nl.tudelft.ewi.se.in4355.server.jobs

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.junit.Before

class JobSourceTest extends JUnitSuite {

  @Test def testThatNextDoesNotReturnTheSameListTwice {

    val js = new JobSource

    //val firstFive = js.next
    //val secondFive = js.next
    //
    //assert(firstFive.length == 5)
    //assert(secondFive.length == 5)
    //
    //assert(firstFive != secondFive)

  }

  @Test def nextFiveLines {

    val js = new JobSource

    println(js.nextFiveParagraphs)

  }

}
