package nl.tudelft.ewi.se.in4355.server

import org.scalatest.junit.JUnitSuite
import org.junit.Test

class JobSourceTest extends JUnitSuite {

  @Test def x {

    val js = new JobSource

    val firstFive = js.next
    val secondFive = js.next

    assert(firstFive.length == 5)
    assert(secondFive.length == 5)

    assert(firstFive != secondFive)

  }

}
