package nl.tudelft.ewi.se.in4355.server

import org.junit.Test
import org.scalatest.junit.JUnitSuite

class OtherTest extends JUnitSuite {

  val jobs = new JobResources

  @Test def test {

    println(jobs.getJobInput.getEntity())

  }

}
