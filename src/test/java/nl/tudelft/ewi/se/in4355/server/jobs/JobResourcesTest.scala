package nl.tudelft.ewi.se.in4355.server.jobs

import org.junit.Test
import org.scalatest.junit.JUnitSuite

class JobResourcesTest extends JUnitSuite {

  val jobs = new JobResources

  @Test def test {

    println(jobs.getJobInput.getEntity())

  }

}
